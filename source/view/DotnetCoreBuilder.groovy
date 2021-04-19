listView('Build Dotnet Core') {
  jobs {
    regex('^Build-DotNetCore-.*?$')
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