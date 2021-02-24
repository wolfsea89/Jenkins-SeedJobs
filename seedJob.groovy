job('seedJob'){
  displayName('Create jobs')
  scm {
    git {
      remote {
          url('git@github.com:wolfsea89/Jenkins-SeedJobs.git')
          credentials('github')
      }
      branch('feature/seedjob')
    }
  }
  steps {
      jobDsl {
          targets('seedJob.groovy')
      }
  }
}