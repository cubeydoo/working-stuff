# Gitlet Design Document

**Name**: Tyler Rathkamp

## Classes and Data Structures
### commit.java
**Instance Variables**

1. Hashmap files — A mapping between file names and SHA value of file contents 
2. String parent — SHA-1 hash value referencing parent commit
3. String timestamp — time at which commit was created
4. String message — A string that stores the commit message

**Static Variables**

1. ArrayList<String> toRemove — array of fileNames that should not be included in the next commit. 
2. Hashmap commitFiles

### commands.java 
**Static Variables**

1. Commit previousCommit — the commit object whose ID is stored in HEAD.txt



## Algorithms
### commands.java 
1. add(File file path): Checks if file exists and prints message if it doesn't. Generate SHA-1 value for file. Get the SHA-1 value for the file stored in the previous commit (if it exists). If identical hash found in previous commit, check staging area for file and delete if present in staging area. Else, stores file in staging area. 
2. log(): Iterates through commits starting at previousCommit until commit.parent = null and concatenates calls  to .toString on each commit to generate log. Prints log -- concatenation into a string necessary to print log in correct order. 
3. globalLog(): Iteratively deserializes every file in .gitlet/refs/commits, and prints their .toString
4. find(String keywords): Iteratively deserializes every commit and check to see if their message contains keywords. Prints their fileName (commit ID) and sets a boolean indicator variable to true if it does. If indicator is false after iteration, print no commit error. 
5. status(): 
	6. Add all branch names (filenames in .gitlet/refs/branches) to an ArrayList and sort. Print each ArrayList element (If branchName.readContents == head.txt.readContents then print an asterisk before the file name). 
	7. Print name of all staged files in staging area. 
	8. Print file names in array list commit.toRemove. 
	9. **Final 2 parts optional**
7. checkout(String commit, String fileName): Deserializes the commit specified by String commit (if commit == null use previousCommit). If commit doesn't exist, print error message. Gets SHA-1 hash of file in previousCommit and finds file contents in .gitlet/Objects. If file doesn't exist, print error message. Rewrites file in working directory with retrieved contents.
8. checkout(String branchHead): Get SHA-1 hash of commit which file branchHead.txt (in .gitlet/refs/branches). If branch does not exist, is current branch, or if untrackedHelper returns a non-empty array(represented by HEAD.txt), print the relevant error message. Otherwise, clear working directory and copy files specified by the branch head's files hashmap into the working directory. 
9. untrackedHelper(String commit): Returns an array of strings which are the names of untracked files in the working directory, using "commit" as the most recent commit. If "commit" is null, use commands.previousCommit. 


### commit.java
1. commit(String message): If staging area empty, print error message. No commit message handled in main. Else: 
	2. Set this.parent to contents of HEAD.txt. 
	3. Set timestamp
	3. Deserialize previous commit, set this.files = parent.files. Remove all files in toRemove and clear toRemove.
	4. For each file in staging area: 
		3. Add its file name (key) and SHA-1 hash (value) to files hashmap (or update hash if name already exists). 
		4. Rename file to SHA-1 hash and move to Objects subdirectory in .gitlet. 
	1. Clear staging area subdirectory and toRemove static variable. 
	2. UPDATE head.txt to SHA-1 value of new commit
	3. Serialize commit and store it in .gitlet/refs/commits with file name of commit's SHA-1 value. 
2. init(): Check if .gitlet folder exists and prints message if it does. Else, ~~creates structure specified in persistence section~~. Creates an initial commit with commit constructor. Files (hashmap) and parent are null. 
3. toString(): Prints commit in form required by log. 
4. rm(String fileName): If file exists in staging area, remove it. Add fileName to static variable toRemove. If file exists in previous commit, delete file.

## Persistence
* head.txt 
* Folder Staging Area
* Folder Objects 
* Folder refs
	* commits 
	* branches 