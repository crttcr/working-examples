// table.st
//
// HTML table using a specific row template
//
// usage:
// st.add("properties", list_of_properties);
//
table(properties) ::= <<
<table border=1>
$header()$
$properties:row()$
</table>
>>