# Gitlet Design Document

**Name**: Animesh Agrawal

```
TODO
  Need to handle Partial commit IDs in checkout, reset
  Handling latest common ancestor for merge commits
  Optional Commands in status
  Remote Commands 
```

## Classes and Data Structures
### commit.java
**Instance Variables**

1. Hashmap files — A mapping between file names and SHA value of file contents 
2. String parent — SHA-1 hash value referencing parent commit
3. String mergedBranch — SHA-1 hash value referencing head of branch that was merged in. 
3. String timestamp — time at which commit was created
4. String message — A string that stores the commit message

**Static Variables**

1. ArrayList<String> toRemove — array of fileNames that should not be included in the next commit. 
2. Hashmap commitFiles

### commands.java 
**Static Variables**

1. Commit latestCommit — the most recent commit upon the time the program is initialized. SHA-1 value is stored in some branch head in .gitlet/refs/branches and HEAD.txt is a path to said branchHead.txt.

## Algorithms
### commands.java 
1. add(File file path): Checks if file exists and prints message if it doesn't. Generate SHA-1 value for file. Get the SHA-1 value for the file stored in the previous commit (if it exists). If identical hash found in previous commit, check staging area for file and delete if present in staging area. Else, stores file in staging area. 
7. checkout(String commit, String fileName): Deserializes the commit specified by String commit (if commit == null use latestCommit). If commit doesn't exist, print error message. Gets SHA-1 hash of file in latestCommit and finds file contents in .gitlet/Objects. If file doesn't exist, print error message. Rewrites file in working directory with retrieved contents. 
8. checkout(String branchHead): Get SHA-1 hash of commit which is the contents of file branchHead.txt (in .gitlet/refs/branches). If branch does not exist, is current branch, or if untrackedHelper returns a non-empty array (SHA-1 that HEAD.txt points to is input), print the relevant error message. Otherwise, clear working directory and copy files specified by the branch head's files hashmap into the working directory. Update HEAD.txt's contents to equal path to .gitlet/refs/branches/branchHead.txt. Clear staging area.
9. untrackedHelper(String commit): Returns an array of strings which are the names of untracked files in the working directory, using "commit" as the most recent commit. If "commit" is null, use commands.latestCommit. 
10. branch(String name): if a file in .gitlet/refs/branches has name == name, print error message. Else, Create a new text file in .gitlet/refs/branches with contents of file pointed to by HEAD.txt (contents will be (a SHA-1 hash). 
11. rm-branch(String name): If file path .gitlet/refs/branches/name.txt equals contents of HEAD.txt or gitlet/refs/branches/name.txt does not exist, print error message. Else, delete .gitlet/refs/branches/name.txt. 
12. reset(String commit): Checks to see if commit with given ID exists, and prints message if it doesn't. Changes the branch in HEAD.txt's commit head in .gitlet/refs/branches to be COMMIT. Calls checkout(String branchHead) on the branch indicated by HEAD.txt afterwards. 
13. splitPoint(String branchName): Takes in a branch name and finds the latest common ancestor of branch denoted by branchName and HEAD.txt. If SHA-1 hash of ancestor = contents of .gitlet/refs/branches/branchName.txt print error message. If SHA-1 hash of ancestor = contents of branch.txt denoted by HEAD.txt, then call checkout(branchName) and print message. Else, return SHA-1 hash of ancestor.
14. unchangedFiles(Commit old, Commit new): Returns an arraylist of the filenames that have not changed between old and new. Ignores files that don't exist in both commits.
15. changedFiles(Commit old, Commit new): Returns an arraylist of the filenames that have changed between old and new. Ignores files that don't exist in both commits.
14. merge(String branchName): If any failure cases occur, print error message. 
	15. Commit branch = Deserialize commit indicated by branchName. 
	16. Commit ancestor = Deserialize commit indicated by RV of splitPoint(branchName)
	17. For all files present in the intersection of the arraylists returned by changedFiles(ancestor, branch) and unchangedFiles(ancestor, latestCommit): check out these files and stage them (with add). 
	18. All files present in branch but not ancestor or latestCommit are checked out and staged. 
	19. For all files in unchangedFiles(ancestor, latestCommit) that are not present in branch's files hashmap: call rm(file) on each of those files. 
	20. ~~For all files in unchangedFiles(ancestor, branch) that are not present in latestCommmit's hashmap: call rm(file) in a try-catch statement ~~
	21. For all files present in changedFiles(ancestor, branch) and changedFiles(ancestor, latestCommit) that do NOT have equal SHA-1 values: replace contents of file in working directory with the concationationed file and staged. 
	22. Commit the changes. If step vii made any changes, print designated message on terminal. 

### display.java
2. log(): Iterates through commits starting at latestCommit until commit.parent = null and concatenates calls  to .toString on each commit to generate log. Prints log -- concatenation into a string necessary to print log in correct order. 
3. globalLog(): Iteratively deserializes every file in .gitlet/refs/commits, and prints their .toString
4. status(): 
	6. Add all branch names (filenames in .gitlet/refs/branches) to an ArrayList and sort. Print each ArrayList element (If branchName == head.txt.readContents then print an asterisk before the file name). 
	7. Print name of all staged files in staging area. 
	8. Print file names in array list commit.toRemove. 
	9. Final 2 parts optional
4. find(String keywords): Iteratively deserializes every commit and check to see if their message contains keywords. Prints their fileName (commit ID) and sets a boolean indicator variable to true if it does. If indicator is false after iteration, print no commit error. 


### commit.java
1. commit(String message): The class constructor. If staging area empty, print error message. No commit message handled in main. Else: 
	2. Set this.parent to SHA-1 value in file pointed to by HEAD.txt. Does not modify mergedBranch.
	3. Set timestamp
	3. Deserialize previous commit, set this.files = parent.files. Remove all files in toRemove and clear toRemove.
	4. For each file in staging area: 
		3. Add its file name (key) and SHA-1 hash (value) to files hashmap (or update hash if name already exists). 
		4. Rename file to SHA-1 hash and move to Objects subdirectory in .gitlet. 
	1.  For each fileName in toRemove
		2.  Remove key-value pair in hashmap files with key fileName
	2. Clear staging area subdirectory and toRemove static variable.
	2. UPDATE head.txt to SHA-1 value of new commit
	3. Serialize commit and store it in .gitlet/refs/commits with file name of commit's SHA-1 value. 
2. commit(String message, String merged): sets instance variable mergedBranch = merge then calls commit(message). 
3. init(): Check if .gitlet folder exists and prints message if it does. Else, ~~creates structure specified in persistence section~~. Creates an initial commit with commit constructor. Files (hashmap) and parent are null. Creates a branch called master (represented by master.txt in .gitlet/refs/branches) which points to the initial commit. 
4. toString(): Prints commit in form required by log. 
5. rm(String fileName): If file exists in staging area, remove it. Add fileName to static variable toRemove. If file exists in previous commit, delete file from working directory.

## Persistence
* head.txt 
* Folder Staging Area
* Folder Objects 
* Folder refs
	* commits 
	* branches 