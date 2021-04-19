import groovy.json.*

String jobsDefinition = 'configuration/jobs.json'

def jobsList = new JsonSlurper().parseText(readFileFromWorkspace(jobsDefinition))

for (jobItem in jobsList){
  if(jobItem.jobType == "Build-Dotnet-Core"){
      def publishJson = new JsonSlurper().parseText("""
        {
          "DockerHubRelease": {
            "repositoryName": "wolfsea89/${jobItem.docker.name}",
            "repositoryCredentialID": "docker_hub",
            "repositoryUrl": "https://index.docker.io/v1/"
          },
          "DockerHubSnapshot": {
            "repositoryName": "wolfsea89/${jobItem.docker.name}-snapshot",
            "repositoryCredentialID": "docker_hub",
            "repositoryUrl": "https://index.docker.io/v1/"
          },
          "GitHubRelease": {
            "repositoryName": "docker.pkg.github.com/wolfsea89/${jobItem.docker.githubRepositoryName}/${jobItem.docker.name}",
            "repositoryCredentialID": "github",
            "repositoryUrl": "https://docker.pkg.github.com/"
          }
        }
        """
      )

    pipelineJob(jobItem.name) {
      parameters{
        stringParam("branchName", jobItem.defaultBranch, 'Branch name')
        stringParam("repositoryUrl", jobItem.repositoryUrl, 'Repository URL (git/https)')
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
              branch('master')
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
            scriptPath('DotnetCore.Jenkinsfile')
          }
        }
      }
    }
  }
}