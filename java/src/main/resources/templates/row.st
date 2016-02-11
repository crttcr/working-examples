// row.st
//
// A single row in an HTML table with hard-coded attribute property names.
//
row(attr) ::= <<
<tr>
   <td>$attr.name$</td>
   <td>$attr.type$</td>
   <td>$attr.key$</td>
   <td>$attr.required$</td>
   <td>$attr.unique$</td>
</tr>

>>
