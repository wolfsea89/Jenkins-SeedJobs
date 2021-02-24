job('seedJob'){
  displayName('Create jobs')
  definition {
    cpsScm{
      scm {
        git {
          remote {
              url('git@github.com:wolfsea89/Jenkins-SeedJobs.git')
              credentials('github')
          }
          branch('feature/seedjob')
        }
      }
    }
  }
  steps {
      dsl {
          external('seedJob.groovy', 'projectB.groovy')
      }
  }
}