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
  git checkout $TRAVIS_PULL_REQUEST_BRANCH
  git commit -am "Travis build $TRAVIS_BUILD_NUMBER"
  echo "Commit End"
}

upload_files() {
  echo "Push Start"
#  git remote add inventory_issue https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git > /dev/null 2>&1
#  git push --quiet --set-upstream inventory_issue dev 
  echo $TRAVIS_PULL_REQUEST_BRANCH
  git push origin $TRAVIS_PULL_REQUEST_BRANCH
  echo "Push End"
}

setup_git
commit_files
upload_files
