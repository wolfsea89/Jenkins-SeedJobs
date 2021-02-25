listView('Tech') {
  jobs {
    name('SeedJob')
    regex('^Tech-.*?$')
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