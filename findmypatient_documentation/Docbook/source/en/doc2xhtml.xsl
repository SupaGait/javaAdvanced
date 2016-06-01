<?xml version='1.0'?> 
<!DOCTYPE stylesheet [
    <!ENTITY dbdir "D:\docbook-xsl-1.79.1">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  
                version="1.0"
                xmlns:d="http://docbook.org/ns/docbook"
                xmlns="http://www.w3.org/1999/xhtml">

  <xsl:import href="&dbdir;/xhtml/docbook.xsl"/> 

  <xsl:output method="xml" indent="yes"/>
  
  <!-- inserting a bar before each sect1 -->
  <xsl:template match="d:sect1">
  	<br />
  	<hr />
  	<xsl:apply-imports />
  </xsl:template>

</xsl:stylesheet>