import groovy.json.*

String jobsDefinition = 'Definitions/jobs.json'

def jobs = new JsonSlurper().parseText(readFileFromWorkspace(jobsDefinition))

for (job in jobs){
  if(job.jobType == "Build"){
    evaluate(new File("BuildsJobs/buildJobs.groovy"))
  }
}