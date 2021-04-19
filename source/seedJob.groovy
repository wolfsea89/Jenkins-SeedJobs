def seedJobs = [
  'source/seedJob.groovy',
  'source/buildDefinitions/buildDocker.groovy',
  'source/buildDefinitions/buildDotnetCore.groovy',
  'source/view/dockerBuilder.groovy',
  'source/view/technicalJobs.groovy'
]

String targetsSeedJobs
for (item in seedJobs){
  if(targetsSeedJobs == null){
    targetsSeedJobs = item + '\n'
  } else {
    targetsSeedJobs += item + '\n'
  }
}

job('SeedJob'){
  displayName('SeedJob')
  description('Create jobs')
    parameters{
        stringParam("branchName", "master", 'Branch name')
      }
  scm {
    git {
      remote {
        url('git@github.com:wolfsea89/Jenkins-SeedJobs.git')
        credentials('github')
      }
      branch('$branchName')
    }
  }
  steps {
    jobDsl {
      targets(targetsSeedJobs)
    }
  }
}