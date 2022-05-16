## Study Material

As part of the study material we provide the Briefing document and Questionaire, as well as the scenarios used during the study (S01-S04)

The Cassandra source code is available in the apache archive https://archive.apache.org/dist/cassandra/

Specifically we have used:
 - [apache-cassandra-1.0.0-beta1](https://archive.apache.org/dist/cassandra/1.0.0/)
 - [pache-cassandra-1.0.0-rc1](https://archive.apache.org/dist/cassandra/1.0.0/)
 - [apache-cassandra-1.0.10](https://archive.apache.org/dist/cassandra/1.0.10/)
 - [apache-cassandra-1.1.1](https://archive.apache.org/dist/cassandra/1.1.1/)
 - [apache-cassandra-1.1.12](https://archive.apache.org/dist/cassandra/1.1.12)
 - [apache-cassandra-1.2.1](https://archive.apache.org/dist/cassandra/1.2.1)



### Tasks:
| Scenario     | Affected Class      | Task |
|--------------|---------------------|------------|
|[S1](S01.docx)            |  CommitAllocator   |  ➡️ _Task 1:_ Class CommitLogAllocator.java is extracted from class CommitLog.java, in version 1.1.1 | 
|              |     | ➡️ _Task 2:_ New functionality is added to class CommitLogAllocator.java in version 1.1.1 from version 1.0.10. | 
|              |     |  | 
|[S2](S02.docx)            |   IMergeIterator   | ➡️ _Task 1:_ Class IMergeIterator.java is extracted as a subclass from class CloseableIterator.java, in version 1.0.0-rc1. | 
|     |  | 
|[S3](S03.docx)            |   Memory, FreeableMemory,RefCountedMemory    | ➡️ _Task 1:_ Class Memory.java is extracted as a superclass from class FreeableMemory.java, in version 1.1.1 | 
|            |      | ➡️ _Task 2:_ Methods originally in FreeableMemory.java ae reallocated between classes Memory.java and RefCountedMemory.java, in version 1.2.1 | 
|            |     | ➡️ _Task 3:_ Class RefCountedMemory.java is added a as a subclass for class Memory.java in version 1.2.1 | 
|     |  | 
|[S4](S04.docx)            |  CommitAllocator   |  ➡️ _Task 1:_ Class CommitLogAllocator.java is extracted from class CommitLog.java, in version 1.1.1 | 













Scenarios:
Task1: Class Migration.java is merged with class AddColumnFamily.java, in version 1.0.11.
	__ Correct		 __ Incorrect		 __ don’t know  
Task2: Class Migration.java is merged with class AddKeySpace.java, in version 1.0.11.
__ Correct		 __ Incorrect		 __ don’t know  
Task3: Class Migration.java is merged with class DropColumnFamily.java, in version 1.0.11.
	__ Correct		 __ Incorrect		 __ don’t know  
Task4: Some Methods originally in class Migration.java are reallocated to ColumnFamilyStore.java and class DefinitionsUpdateVerbHandler.java in version 1.1.1.
__ Correct		 __ Incorrect		 __ don’t know  



