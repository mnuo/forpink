#!/bin/sh

git filter-branch -f --env-filter '

OLD_EMAIL="mnuo1115"
OLD_EMAIL1="mon@hwagain.com"
CORRECT_NAME="mnuo"
CORRECT_EMAIL="mnuo1115@163.com"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ] || [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL1" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ] || [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL1" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags