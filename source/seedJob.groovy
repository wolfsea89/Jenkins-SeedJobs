def seedJobs = [
  'source/seedJob.groovy',
  'source/view/dockerBuilder.groovy'
]

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
        for (item in seedJobs){
          "$item "
        }
      )
    }
  }
}