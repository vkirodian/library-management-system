#!/bin/sh

setup_git() {
  echo "Configuration Start"
  git config --global user.email ${EMAIL}
  git config --global user.name ${USERNAME}
#  git remote set-url origin https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git
  echo "Configuration End"
}

commit_files() {
  echo "Commit Start"
  git checkout test_jar_cop
  git commit -am "Travis build $TRAVIS_BUILD_NUMBER"
  echo "Commit End"
}

upload_files() {
  echo "Push Start"
  git remote add develop https://${GIT_TOKEN}@github.com/vkirodian/library-management-system.git > /dev/null 2>&1
  git push --quiet --set-upstream develop  test_jar_cop 
  echo "Push End"
}

setup_git
commit_files
upload_files
