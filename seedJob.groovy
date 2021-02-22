import groovy.json.*

String jobsDefinition = 'Definitions/jobs.json'

def jobs = new JsonSlurper().parseText(readFileFromWorkspace(jobsDefinition))

for (job in jobs){
  if(job.jobType == "Build"){
        def publishJson = new JsonSlurper().parseText("""
      {
        "DockerHubRelease": {
          "repositoryName": "wolfsea89/${job.docker.name}",
          "repositoryCredentialID": "docker_hub",
          "repositoryUrl": "https://index.docker.io/v1/"
        },
        "DockerHubSnapshot": {
          "repositoryName": "wolfsea89/${job.docker.name}-snapshot",
          "repositoryCredentialID": "docker_hub",
          "repositoryUrl": "https://index.docker.io/v1/"
        },
        "GitHubRelease": {
          "repositoryName": "docker.pkg.github.com/wolfsea89/jenkins-baseimage/${job.docker.name}",
          "repositoryCredentialID": "github",
          "repositoryUrl": "https://docker.pkg.github.com/"
        }
      }
      """)

    pipelineJob(job.name) {
      parameters{
        stringParam("branchName", job.defaultBranch, 'Branch name')
        stringParam("repositoryUrl", job.repositoryUrl, 'Repository URL (git/https)')
        stringParam("manualVersion", "", 'Set manual version (X.Y.Z). Worked with branch release, hotfix, master without version')
      }
      environmentVariables {
        env('JENKINSFILE_SCRIPTS_DIR', '.jenkins')
        env('GIT_CREDS_ID', 'github')
        env('APP_CONFIGURATION_JSON_PATH', 'configuration/jenkins.json')
        env('BASEIMAGE_SERVICES_ADMIN_CREDS_ID', 'baseImage_services_AminPassword')
        env('PUBLISH_REPOSITORIES', JsonOutput.toJson(publishJson))
      }
      definition {
        cpsScm{
          scm {
            git {
              remote {
                  url('git@github.com:wolfsea89/Jenkins-Ci-Jenkinsfiles.git')
                  credentials('github')
              }
              branch('feature/seedjob')
              extensions {
                wipeOutWorkspace()
                submoduleOptions {
                  disable(false)
                  parentCredentials(true)
                  recursive(false)
                  tracking(true)
                }
              }
            }
            scriptPath('Jenkinsfile')
          }
        }
      }
    }
  }
}

listView('Build') {
  // jobFilters {
  //   regex {
  //     regex(/^Build-.*?$/)
  //   }
  // }
  jobs {
    name("")
    regex('^Build-.*?$')
  }
  columns {
    status()
    weather()
    name()
    lastSuccess()
    lastFailure()
    lastDuration()
    buildButton()
  }
}