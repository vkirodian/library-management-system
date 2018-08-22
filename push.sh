#!/bin/sh

setup_git() {
  git config --global user.email ${EMAIL}
  git config --global user.name ${USERNAME}
}

commit_website_files() {
  git checkout -b develop
  git add . /tmp/lib/*.jar
  git commit --message "Travis build"
}

upload_files() {
  git remote add origin-pages https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git > /dev/null 2>&1
  git push --quiet --set-upstream origin-pages develop 
}

setup_git
commit_website_files
upload_files
