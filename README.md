# todo-list

+-----------------+
|       GUI       |
+-----------------+
| + start(): void |
+-----------------+

+-----------------+
| <<enumeration>> |
| ItemState       |
+-----------------+
| Done            |
| Incomplete      |
| Overdue         |
+-----------------+

+-----------------------------------------------------+
|                        Item                         |
+-----------------------------------------------------+
| - dueDate: Localdate                                |
| - name: String                                      |
| - done: String                                      |
| - itemState: ItemState                              |
+-----------------------------------------------------+
| + Item()                                            |
| + Item(name:String)                                 |
| + Item(name:String, done:String)                    |
| + Item(name:String, done:String, dueDate:Localdate) |
| + markAsDone(): void                                |
| + markAsIncomplete(): void                          |
| + markAsOverdue(): void                             |
| + setItemState(): void                              |
| + toString(): String                                |
+-----------------------------------------------------+

+---------------------------------------+
|                 Main                  |
+---------------------------------------+
| + startGUI(): void                    |
| + addDueDate(date:LocalDate): boolean |
| + makeTodoList(name:String): TodoList |
| + main(args:String[]): void           |
+---------------------------------------+

+---------------------------------------------------------+
|                         Config                          |
+---------------------------------------------------------+
| - final EMAIL: String                                   |
| - final PASSWORD: String                                |
| - final CARRIER: String                                 |
| - final PORT: String                                    |
+---------------------------------------------------------+
| + Config(email:String, password:String, carrier:String) |
| + getOS(): String                                       |
| + writeDefaultConfig(): void                            |
| + determineCarrierExtension(): String                   |
| + createUserConfig(): void                              |
| + parseUserConfig(): Config                             |
| + configue(): void                                      |
+---------------------------------------------------------+

+--------------------------------------+
|               TodoList               |
+--------------------------------------+
| - list: ArrayList<Item>              |
| - path: String                       |
| - name: String                       |
+--------------------------------------+
| + TodoList()                         |
| + TodoList(name:String)              |
| + TodoList(name:String, path:String) |
| + checkPathExists(): boolean         |
| + writeToFile(): void                |
| + readFromFile(): String             |
| + delete(): void                     |
| + create(): void                     |
+--------------------------------------+

