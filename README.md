# earthquake-finder

# Mehmet Baran Nakipoğlu

The project is based on the API of https://earthquake.usgs.gov/fdsnws/event/1/

It tries to get a country name and the number of days from the user to find the earthquakes happened around that country for the given days.
parameters: countryName, countOfDays
Controller --> GET Reqeust ("/query")
There is a list of (a JSON file) countries with their respective latitude and longitude information and more.

For those who will run the project on their local machine; can use the (e.g.)
http://localhost:8080/query/?countryName=Turkey&countOfDays=10

where the base URL is http://localhost:8080/query/
and there are two params as aforementioned
