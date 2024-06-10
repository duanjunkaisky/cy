{
  "bookingType": "NEW",
  "originLocation": {
    "geoId": "${fromPortId}",
    "rkstCode": "${fromPortCode}",
    "countryCode": "${fromPortCountryCode}"
  },
  "destinationLocation": {
    "geoId": "${toPortId}",
    "rkstCode": "${toPortCode}",
    "countryCode": "${toPortCountryCode}"
  },
  "batchSizeInWeeks": 2,
  "productsFetchSizeInWeeks": 2,
  "validationFlags": {
    "checkEquipmentAvailability": true,
    "checkDeadlines": true,
    "checkSpace": true,
    "checkYield": true,
    "checkFixedPrices": true
  },
  "carrierCode": "MAEU",
  "exportServiceMode": "CY",
  "importServiceMode": "CY",
  "cargoDetails": {
    "commodityCode": "004403",
    "cargoType": "DRY",
    "isDangerous": false,
    "isTempControlled": false
  },
  "containerDetails": [
    {
      "size": "${containerSize}",
      "type": "${containerType}",
      "height": "${containerHeight}",
      "quantity": 1,
      "cargoWeight": "${cargoWeight}",
      "weightMeasurementUnit": "KGS",
      "isNOR": false,
      "isFoodGrade": false,
      "isoCode": "${containerCode}",
      "isShipperOwned": false,
      "isOutOfGauge": false,
      "isImportReturned": false,
      "containerRefs": [
        "8R70CQ9OQAKE8"
      ]
    }
  ],
  "metricUnits": true,
  "isContainerPickedUp": false,
  "isContainerGatedIn": false,
  "earliestDepartureDate": "${departureDate}",
  "priceCalculationDate": "${departureDate}",
  "parties": [
    {
      "scvCode": "40604978695",
      "cmdCode": "CN04978695",
      "roleName": "Booked By",
      "roleCode": 1,
      "companyName": "QINGDAO HIGHWAY CLOUD TECHNOLOGY DEVELOPMENT CO.,LTD.",
      "companyAddress": "40 , HONGKONG MIDDLE ROAD , QINGDAO , CHINA , 266000"
    },
    {
      "scvCode": "40604978695",
      "cmdCode": "CN04978695",
      "roleName": "Price Owner",
      "companyName": "QINGDAO HIGHWAY CLOUD TECHNOLOGY DEVELOPMENT CO.,LTD.",
      "roleCode": 44
    }
  ],
  "buCode": "2162",
  "productsEagerFetch": true
}