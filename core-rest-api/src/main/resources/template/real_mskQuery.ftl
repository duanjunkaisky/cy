{
  "alternateOfferRequest": true,
  "from": {
    "maerskGeoId": "${fromPortId}",
    "maerskRkstCode": "${fromPortCode}",
    "countryCode": "${fromPortCountry}",
    "maerskServiceMode": "CY",
    "name": "${fromPortFullName}"
  },
  "to": {
    "maerskGeoId": "${toPortId}",
    "maerskRkstCode": "${toPortCode}",
    "countryCode": "${toPortCountry}",
    "maerskServiceMode": "CY",
    "name": "${toPortFullName}"
  },
  "containers": [
    {
      "isoCode": "${containerCode}",
      "size": "${containerSize}",
      "type": "${containerType}",
      "weight": 18000,
      "quantity": 1,
      "isReefer": false,
      "isNonOperatingReefer": false,
      "isShipperOwnedContainer": false
    }
  ],
  "shipmentPriceCalculationDate": "${queryDate}",
  "commodity": {
    "id": "004403",
    "isDangerous": false,
    "dangerousDetails": []
  },
  "unit": "KG",
  "brandCode": "MAEU",
  "customerCode": "40605170076",
  "loadAFLS": false,
  "weekOffset": ${weekOffset}
}