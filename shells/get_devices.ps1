# list all currently connect devices

Get-PnpDevice -PresentOnly | Sort-Object -Property Name | ft name -AutoSize



