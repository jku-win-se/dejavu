## Study Material

As part of the study material we provide the Briefing document and Questionaire, as well as the Stories used during the study (S01-S04)

The Cassandra source code is available in the apache archive https://archive.apache.org/dist/cassandra/

Specifically we have used:
 - [apache-cassandra-1.0.0-beta1](https://archive.apache.org/dist/cassandra/1.0.0/)
 - [pache-cassandra-1.0.0-rc1](https://archive.apache.org/dist/cassandra/1.0.0/)
 - [apache-cassandra-1.0.10](https://archive.apache.org/dist/cassandra/1.0.10/)
 - [apache-cassandra-1.1.1](https://archive.apache.org/dist/cassandra/1.1.1/)
 - [apache-cassandra-1.1.12](https://archive.apache.org/dist/cassandra/1.1.12)
 - [apache-cassandra-1.2.1](https://archive.apache.org/dist/cassandra/1.2.1)



### Stories and Change Scenarios:
| Story     | Affected Class      | Task | Change Scenario| 
|--------------|---------------------|------------|------------|
|[S1](https://github.com/jku-win-se/dejavu/raw/main/studymaterial/S01.docx)            |  CommitAllocator   |  ➡️ _Task 1:_ Class CommitLogAllocator.java is extracted from class CommitLog.java, in version 1.1.1 | CS2 |
|              |     | ➡️ _Task 2:_ New functionality is added to class CommitLogAllocator.java in version 1.1.1 from version 1.0.10. | CS1 |
|              |     |  | 
|[S2](https://github.com/jku-win-se/dejavu/raw/main/studymaterial/S02.docx)            |   IMergeIterator   | ➡️ _Task 1:_ Class IMergeIterator.java is extracted as a subclass from class CloseableIterator.java, in version 1.0.0-rc1. | CS5 |
|     |  | 
|[S3](https://github.com/jku-win-se/dejavu/raw/main/studymaterial/S03.docx)            |   Memory, FreeableMemory,RefCountedMemory    | ➡️ _Task 1:_ Class Memory.java is extracted as a superclass from class FreeableMemory.java, in version 1.1.1 | CS6 |
|            |      | ➡️ _Task 2:_ Methods originally in FreeableMemory.java ae reallocated between classes Memory.java and RefCountedMemory.java, in version 1.2.1 | CS8 |
|            |     | ➡️ _Task 3:_ Class RefCountedMemory.java is added a as a subclass for class Memory.java in version 1.2.1 | CS5 |
|     |  | 
|[S4](https://github.com/jku-win-se/dejavu/raw/main/studymaterial/S04.docx)            |  Migration, AddKeySpace, DropColumnFamily, ColumnFamilyStore, DefinitionsUpdateVerbHandler   |  ➡️ _Task 1:_ Class Migration.java is merged with class AddColumnFamily.java, in version 1.0.11.| CS6 |
|            |     | ➡️ _Task 2:_ Class Migration.java is merged with class AddKeySpace.java, in version 1.0.11 | CS9 |
|            |     | ➡️ _Task 3:_ Class Migration.java is merged with class DropColumnFamily.java, in version 1.0.11 | CS9 |
|            |     | ➡️ _Task 4:_ Some Methods originally in class Migration.java are reallocated to ColumnFamilyStore.java and class DefinitionsUpdateVerbHandler.java in version 1.1.1. | CS4 |
