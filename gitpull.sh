#!/bin/bash

# Script so that we don't forget to branch locally!

LOCAL_BRANCH=$1
REMOTE_BRANCH="developer"

[ -z "$LOCAL_BRANCH" ] && echo "Usage: `basename $0` LOCAL_BRANCH_NAME" >&2 && exit;

git pull origin $REMOTE_BRANCH
git checkout -b $LOCAL_BRANCH $REMOTE_BRANCH

# After that: 
# 1. Do stuff
# 2. git add and git commit your changes and files
# 3. git checkout $REMOTE_BRANCH (switch to the remote branch)
# 4. git merge –-no-ff $LOCAL_BRANCH (no fast forward merge)
# 5. git branch –d $LOCAL_BRANCH (delete the local branch)
# 6. git push origin $REMOTE_BRANCH
