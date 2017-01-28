var request = require('request-promise')
var _ = require('lodash')
var fs = require('fs-promise')

var holidaysEndpoint = 'https://www.gov.uk/bank-holidays.json'
var outputFileName = 'bank-holiday-dates'

main()

function main() {
  getHolidayDataFromEndpoint()
    .then(exportDatesFile)
    .then(reportSuccess)
    .catch(quitWithError)
}

function getHolidayDataFromEndpoint() {
  options = {
    uri: holidaysEndpoint,
    json: true
  }
  return request(options)
}

function exportDatesFile(holidayData) {
  var holidayEvents = holidayData["england-and-wales"].events
  var datesFile = _(holidayEvents)
    .map('date')
    .join('\n') + '\n'
  return writeDatesFile(datesFile)
}

function writeDatesFile(body) {
  return fs.writeFile(`./${outputFileName}`, body)
}

function reportSuccess() {
  console.log(`Dates file successfully written to ${outputFileName}`);
}

function quitWithError(error) {
  console.log(`An error occurred: ${error}`);
  process.exit(1)
}
