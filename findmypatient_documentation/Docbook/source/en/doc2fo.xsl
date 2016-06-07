<?xml version='1.0'?> 
<!DOCTYPE stylesheet [
    <!ENTITY dbdir "C:\_ProjectsEpita\docbook-xsl-1.79.1">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  
                version="1.0"
                xmlns:d="http://docbook.org/ns/docbook"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:import href= "C:\_ProjectsEpita\docbook-xsl-1.79.1/fo/docbook.xsl"/> 

	<xsl:output method="xml" indent="yes"/>

	<!-- setting paper size -->
	<xsl:param name="paper.type" select="'A4'"/>

	<!-- titles with the same typeface as the text (default is 'sans-serif') -->
	<xsl:param name="title.font.family" select="'serif'"/>

	<!-- ensuring a page break before first-level sections -->
	<xsl:attribute-set name="section.level1.properties">
		<xsl:attribute name="break-before">page</xsl:attribute>
	</xsl:attribute-set>

	<!-- Show links -->
	<xsl:attribute-set name="xref.properties">
		<xsl:attribute name="color">#00009F</xsl:attribute>
		<xsl:attribute name="font-style">italic</xsl:attribute>
	</xsl:attribute-set>

  	<!-- Print requirement before label -->
	<xsl:template match="//orderedlist[@id='requirements']/listitem">
		<fo:list-item space-after="0.5ex">
		    <fo:list-item-label start-indent="1em">
		        <fo:block>
		            REQ <xsl:number/>
		        </fo:block>
		    </fo:list-item-label>
		    <fo:list-item-body>
		        <fo:block>
		            <xsl:apply-templates/>
		        </fo:block>
		    </fo:list-item-body>
		</fo:list-item>
	</xsl:template>

</xsl:stylesheet>