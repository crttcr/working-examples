// row.st
//
// A single row in an HTML table with hard-coded attribute properties.
// Obviously, this isn't a reusable element in an output grammar.
// But it suffices to get this example to work.
//
row(attr) ::= <<
<tr>
   $attr.name:cell()$
   $attr.type:cell()$
   $attr.key:cell()$
   $attr.required:cell()$
   $attr.unique:cell()$
</tr>

>>
