
package es.ujaen.dae.localidadescercanas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ciudad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ciudad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="latitud" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="longitud" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ciudad", propOrder = {
    "latitud",
    "longitud",
    "nombre"
})
public class Ciudad {

    protected float latitud;
    protected float longitud;
    protected String nombre;

    /**
     * Gets the value of the latitud property.
     * 
     */
    public float getLatitud() {
        return latitud;
    }

    /**
     * Sets the value of the latitud property.
     * 
     */
    public void setLatitud(float value) {
        this.latitud = value;
    }

    /**
     * Gets the value of the longitud property.
     * 
     */
    public float getLongitud() {
        return longitud;
    }

    /**
     * Sets the value of the longitud property.
     * 
     */
    public void setLongitud(float value) {
        this.longitud = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

}
