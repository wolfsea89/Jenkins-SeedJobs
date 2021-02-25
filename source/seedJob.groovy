def seedJobs = [
  'source/seedJob.groovy',
  'source/view/dockerBuilder.groovy',
  'source/view/technicalJobs.groovy',
  'source/buildDefinitions/buildDocker.groovy'
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
  scm {
    git {
      remote {
        url('git@github.com:wolfsea89/Jenkins-SeedJobs.git')
        credentials('github')
      }
      branch('master')
    }
  }
  steps {
    jobDsl {
      targets(targetsSeedJobs)
    }
  }
}