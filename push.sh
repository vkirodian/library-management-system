#!/bin/sh

setup_git() {
  echo "Configuration Start"
  git config --global user.email ${EMAIL}
  git config --global user.name ${USERNAME}
  echo "Configuration End"
}

commit_website_files() {
  echo "Checkout and Commit Start"
  git checkout -b develop
  git add . /tmp/lib/*.jar
  git commit --message "Travis build"
  echo "Checkout and Commit End"
}

upload_files() {
  echo "Push Start"
  git remote add origin-pages https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git > /dev/null 2>&1
  git push --quiet --set-upstream origin-pages develop 
  echo "Push End"
}

setup_git
commit_website_files
upload_files
