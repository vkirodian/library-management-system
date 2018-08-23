#!/bin/sh

setup_git() {
  echo "Configuration Start"
  git config --global user.email ${EMAIL}
  git config --global user.name ${USERNAME}
  git remote set-url origin https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git
  echo "Configuration End"
}

commit_files() {
  echo "Commit Start"
#  git checkout -b dev
  git commit -am "Travis build $TRAVIS_BUILD_NUMBER"
  echo "Commit End"
}

upload_files() {
  echo "Push Start"
#  git remote add inventory_issue https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git > /dev/null 2>&1
#  git push --quiet --set-upstream inventory_issue dev 
  branch_name=`git rev-parse --abbrev-ref HEAD`
  git push origin $branch_name
  echo "Push End"
}

setup_git
commit_files
upload_files
