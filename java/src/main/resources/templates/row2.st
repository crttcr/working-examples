// row2.st
//
// A single row in an HTML table with hard-coded attribute property names.
// This was the first attempt before factoring out the part that wrapped a
// cell with <td> </td> markup.
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
