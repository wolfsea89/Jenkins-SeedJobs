listView('Tech') {
  jobs {
    name('seedJob')
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