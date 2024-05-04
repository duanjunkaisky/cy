{
  "originLoc": "${fromPortCode}",
  "originLocationType": "CY",
  "destinationLoc": "${toPortCode}",
  "destinationLocationType": "CY",
  "containers": [
    <#if containerCode = "22G1">
      {
        "cargoType": "DR",
        "equipmentIsoCode": "22G1",
        "equipmentName": "DRY 20",
        "quantity": 1,
        "equipmentSize": "20",
        "equipmentONECntrTpSz": "D2",
        "isFocus": false,
        "isError": false,
        "commodityGroups": [
          {
            "commodityGroup": "FAK"
          }
        ]
      }
    </#if>
    <#if containerCode = "42G1">
      {
        "cargoType": "DR",
        "equipmentIsoCode": "42G1",
        "equipmentName": "DRY 40",
        "quantity": 1,
        "equipmentSize": "40",
        "equipmentONECntrTpSz": "D4",
        "isFocus": false,
        "isError": false,
        "commodityGroups": [
          {
            "commodityGroup": "FAK"
          }
        ]
      }
    </#if>
    <#if containerCode = "45G1">
      {
        "cargoType": "DR",
        "equipmentIsoCode": "45G1",
        "equipmentName": "DRY 40H",
        "quantity": 1,
        "equipmentSize": "40H",
        "equipmentONECntrTpSz": "D5",
        "isFocus": false,
        "isError": false,
        "commodityGroups": [
          {
            "commodityGroup": "FAK"
          }
        ]
      }
    </#if>
  ],
  "commodityCode": "000000",
  "reeferType": "",
  "isSoc": false,
  "commodityGroups": [],
  "serviceScopeCode": "AEW"
}