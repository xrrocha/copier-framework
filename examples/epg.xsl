<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:template match="raiz">
		<documentomasivo>
			<xsl:apply-templates select="registro" />
		</documentomasivo>
	</xsl:template>

	<xsl:template match="registro">
		<tercero>
			<tipodocumento><xsl:value-of select="tipoDocumento"/></tipodocumento>
			<numerodocumento><xsl:value-of select="numeroDocumento"/></numerodocumento>
			<informacionpresupuestal>
				<registropresupuestal>
					<iddependencia><xsl:value-of select="idDependencia"/></iddependencia>
					<idconcepto><xsl:value-of select="idConcepto"/></idconcepto>
					<valor><xsl:value-of select="valor"/></valor>
				</registropresupuestal>
			</informacionpresupuestal>
			<deducciones>
				<registrodeducciones>
					<idconceptodescuento><xsl:value-of select="idConceptoDescuento"/></idconceptodescuento>
					<tercerobeneficiario>
						<tipodocumento><xsl:value-of select="tipoDocumentoBeneficiario"/></tipodocumento>
						<numerodocumento><xsl:value-of select="numeroDocumentoBeneficiario"/></numerodocumento>
					</tercerobeneficiario>
					<valor><xsl:value-of select="valorDescuento"/></valor>
				</registrodeducciones>
			</deducciones>
			<mediodepago>
				<idmediodepago><xsl:value-of select="idMedioPago"/></idmediodepago>
				<cuentabancaria>
					<tipodocumento><xsl:value-of select="tipoDocumentoEntidadFinanciera"/></tipodocumento>
					<identidadfinanciera><xsl:value-of select="numeroDocumentoEntidadFinanciera"/></identidadfinanciera>
					<tipodecuenta><xsl:value-of select="tipoCuenta"/></tipodecuenta>
					<numerodecuenta><xsl:value-of select="numeroCuenta"/></numerodecuenta>
				</cuentabancaria>
			</mediodepago>
			<valortotal><xsl:value-of select="valorTotal"/></valortotal>
			<valordeducciones><xsl:value-of select="valorDeducciones"/></valordeducciones>
			<valorneto><xsl:value-of select="valorNeto"/></valorneto>
		</tercero>
	</xsl:template>
</xsl:stylesheet>