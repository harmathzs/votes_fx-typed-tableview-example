# JavaFX Voting Table: Filters and Summary

## 1. Task objective

Complete the provided JavaFX voting application by implementing:

- filters for the voter table;
- validation for filter input; and
- a summary based on the rows currently displayed in the table.

The application already contains a JavaFX `TableView`, a model class named `Vote`, and 120 generated Hungarian voter records in `VotesData`.

## 2. Learning goals

After completing this task, you will be able to:

- use JavaFX controls and FXML together;
- inject FXML controls into a controller;
- configure table columns with typed cell value factories;
- filter an observable collection with `FilteredList`;
- combine multiple filter conditions using AND logic;
- validate user input and display error messages; and
- calculate summary values from the rows displayed in the table.

## 3. Existing application

The starting application provides:

- a TableView with ID, first name, last name, birthday, address, and voted party columns;
- a `Vote` class with an ID, name, `LocalDate` birthday, address, and party;
- 120 generated records in `VotesData.votes`; and
- FXML areas for filters on the left and summary information on the right.

You may modify the FXML, controller, and supporting Java classes. Do not change the basic project structure unnecessarily.

## 4. Required layout

The application should present the following areas:

```text
+----------------------+-------------------------------------+----------------------+
| Filters              | Voter table                         | Summary              |
+----------------------+-------------------------------------+----------------------+
|                      |                                     |                      |
| Name                 |  ID | First Name | Last Name | ...  | Displayed: 0         |
| [______________]     |-------------------------------------| TISZA: 0 (0.0%)      |
|                      |                                     | FIDESZ: 0 (0.0%)     |
| Party                |                                     | Mi Hazánk: 0 (0.0%)  |
| [ All              v]|                                     |                      |
|                      |                                     | Average age: -       |
| Birthday             |                                     | Youngest: -          |
| From [__________]    |                                     | Oldest: -            |
| To   [__________]    |                                     | Most common town: -  |
|                      |                                     |                      |
| [ Apply ] [ Reset ]  |                                     |                      |
|                      |                                     |                      |
+----------------------+-------------------------------------+----------------------+
```

The table-like layout above is representative. Improve the spacing and labels as long as all required controls are present and easy to use.

## 5. Filter requirements

### 5.1 Name filter

Provide a text field for filtering names.

- The filter may search the full name or the first and last names separately.
- Matching must be case-insensitive.
- A blank text field must have no effect.
- Whitespace at the beginning or end of the entered text should be ignored.

For example, searching for `Kata` may find `Katalin` and/or names containing `Kata`.

### 5.2 Party filter

Provide a choice control or combo box with these exact options:

```text
All
TISZA
FIDESZ
Mi Hazánk
```

- `All` must show records from every party.
- Selecting a party must show only records whose `votedParty` is equal to that party.
- The party control should default to `All`.

### 5.3 Birthday range filter

Provide two date controls:

- **From** — the earliest birthday to include;
- **To** — the latest birthday to include.

A birthday range is inclusive: a record with a birthday equal to **From** or **To** must be displayed.

Both date fields are optional. If one is empty, filtering is performed only from the available boundary.

Examples:

- `From = 1980-01-01`, `To = empty`: show birthdays from 1980 onward.
- `From = empty`, `To = 2000-12-31`: show birthdays up to the end of 2000.
- `From = 1980-01-01`, `To = 2000-12-31`: show birthdays in that inclusive range.

### 5.4 AND logic

All active filter conditions must be combined using AND logic.

For example, selecting `FIDESZ` and entering `Nagy` must show only FIDESZ voters whose names match `Nagy`. A birthday range further limits the same result set.

### 5.5 Apply and Reset buttons

#### Apply

- validate the birthday range;
- combine the entered filters into one predicate;
- update the TableView; and
- update the summary from the resulting displayed rows.

#### Reset

- clear the name field;
- set the party selector to `All`;
- clear both birthday date fields;
- remove all filtering restrictions;
- show all 120 records; and
- update the summary.

## 6. Validation requirement

The birthday values must be checked before filtering is applied.

Validation rules:

1. Both date fields may be empty.
2. If both dates are set, **To** must not be earlier than **From**.
3. Invalid or reversed ranges must not be silently applied.
4. A clear validation message must be displayed next to the birthday fields or elsewhere in the filter panel.

Use a message similar to:

```text
Validation error: the "To" date must not be earlier than the "From" date.
```

A JavaFX `DatePicker` is recommended because it produces a `LocalDate` value directly. The date fields may remain blank. Do not apply a different filter while the birthday range is invalid.

## 7. Required summary

The summary panel must be updated whenever `Apply` or `Reset` is used and must describe only the records currently displayed in the table.

Display:

1. **Displayed** — the number of currently displayed voter records.
2. **TISZA** — the number and percentage of displayed records voted for TISZA.
3. **FIDESZ** — the number and percentage of displayed records voted for FIDESZ.
4. **Mi Hazánk** — the number and percentage of displayed records voted for Mi Hazánk.
5. **Average age** — the arithmetic mean age of the displayed records.
6. **Youngest** — the youngest displayed voter's age.
7. **Oldest** — the oldest displayed voter's age.
8. **Most common town** — the town appearing most often in displayed addresses.

Example summary for the unfiltered dataset:

```text
Displayed: 120
TISZA: 36 (30.0%)
FIDESZ: 72 (60.0%)
Mi Hazánk: 12 (10.0%)

Average age: 54
Youngest: 44
Oldest: 76
Most common town: Budapest
```

The percentages are illustrative. The actual generated party distribution and ages must be used; calculate the values rather than hard-coding the example.

### 7.1 Empty result behavior

If no records match the filters:

- `Displayed` must be `0`;
- each party count and percentage must be `0 (0.0%)`;
- `Average age`, `Youngest`, and `Oldest` must show `-`; and
- `Most common town` must show `-`.

### 7.2 Calculations

- Age must be calculated from each voter's birthday and the current date or a task-defined reference date.
- Percentages must be calculated as:

```text
party count / displayed row count * 100
```

- Format party percentages with one decimal place, for example `33.3%`.
- If more than one town has the same highest count, choose and document a deterministic rule, such as the first town encountered in table order.

## 8. Table behavior

Configure the six existing table columns so they display the corresponding `Vote` fields.

Recommended FXML column IDs:

- `idColumn`;
- `firstNameColumn`;
- `lastNameColumn`;
- `birthdayColumn`;
- `addressColumn`;
- `partyColumn`.

The table should accept sorting normally. Filtering and summary values must continue to use the filtered rows rather than the complete source data.

## 9. Technical recommendations

The recommended implementation is:

```java
ObservableList<Vote> source = FXCollections.observableArrayList(VotesData.votes);
FilteredList<Vote> filtered = new FilteredList<>(source, vote -> true);
SortedList<Vote> sorted = new SortedList<>(filtered);

sorted.comparatorProperty().bind(votesTableView.comparatorProperty());
votesTableView.setItems(sorted);
```

A `Predicate<Vote>` must return `true` when a record satisfies every active filter.

Example filter structure:

```java
filtered.setPredicate(vote -> {
    boolean matchesName = true;
    boolean matchesParty = true;
    boolean matchesBirthday = true;

    // Assign the filter conditions here.

    return matchesName && matchesParty && matchesBirthday;
});
```

Update the summary after changing the filtered list. Avoid manually hard-coding the 120 visible records or recalculating from `VotesData.votes` instead of the filtered collection.

## 10. Acceptance criteria

The solution is complete when:

- [ ] all table columns display values;
- [ ] the name filter works case-insensitively;
- [ ] all four party options work correctly;
- [ ] empty and one-sided birthday ranges work correctly;
- [ ] two-sided birthday ranges include both boundaries;
- [ ] filters are combined using AND logic;
- [ ] reversed birthday ranges show a clear error and do not apply filtering;
- [ ] `Apply` updates both the table and summary;
- [ ] `Reset` restores all 120 records and default controls;
- [ ] all five summary labels/values are visible and correct;
- [ ] an empty result is handled without divide-by-zero or exceptions;
- [ ] the application builds and runs without errors.

## 11. Recommended milestones

1. Confirm that the table columns show all `Vote` fields.
2. Add the filter controls and labels to the FXML.
3. Create an observable source list and wrap it in `FilteredList`.
4. Implement party filtering.
5. Implement text filtering.
6. Implement birthday range filtering and validation.
7. Implement AND logic and the `Apply` button.
8. Implement `Reset`.
9. Implement summary calculations.
10. Test normal, combined, invalid, reversed, and empty-result cases.

## 12. Testing scenarios

Test at least the following cases:

| Scenario | Party | Name | From | To | Expected result |
|---|---|---|---|---|---|
| Initial state | All | blank | blank | blank | All 120 records |
| Party only | FIDESZ | blank | blank | blank | FIDESZ records only |
| Name only | All | `Kata` | blank | blank | Matching names only |
| Date only | All | blank | 1980-01-01 | 2000-12-31 | Birthdays in the inclusive range |
| Combined filters | TISZA | `Nagy` | 1950-01-01 | 2000-12-31 | TISZA + Nagy + date-range matches |
| Reversed range | All | blank | 2000-01-01 | 1980-01-01 | No filter applied; validation message shown |
| No result | Mi Hazánk | unlikely-name | blank | blank | 0 displayed; valid empty summary |
| Reset | All | blank | blank | blank | All 120 records; summary reset |

## 13. Submission

Submit:

- the complete JavaFX project;
- modified FXML and Java source files;
- a short description of how filtering and summary calculations work; and
- the test cases used to verify the implementation.

Do not submit generated build folders such as `target` or IDE-specific files.

## 14. Grading rubric

| Requirement | Points |
|---|---:|
| Table values and user interface layout | 10 |
| Name text filter | 10 |
| Party filter | 10 |
| Birthday range filtering | 15 |
| AND logic and Apply/Reset behavior | 15 |
| Input validation and error message | 15 |
| Correct summary calculations | 20 |
| Empty-result handling and code quality | 5 |
| **Total** | **100** |

