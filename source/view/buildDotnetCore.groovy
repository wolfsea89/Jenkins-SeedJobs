listView('Build Docker') {
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