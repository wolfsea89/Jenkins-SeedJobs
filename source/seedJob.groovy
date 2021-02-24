def seedJobs = [
  'source/seedJob.groovy',
  'source/view/dockerBuilder.groovy'
]

String targetsSeedJobs
for (item in seedJobs){
  targetsSeedJobs += item + ' '
}
job('seedJob'){
  displayName('seedJob')
  description('Create jobs')
  scm {
    git {
      remote {
        url('git@github.com:wolfsea89/Jenkins-SeedJobs.git')
        credentials('github')
      }
      branch('feature/create_seedjob')
    }
  }
  steps {
    jobDsl {
      targets(
     
      )
    }
  }
}