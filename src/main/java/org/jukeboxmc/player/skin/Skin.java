package org.jukeboxmc.player.skin;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Skin {

    public static final int SINGLE_SKIN_SIZE = 8192;
    public static final int DOUBLE_SKIN_SIZE = 16384;
    public static final int SKIN_128_64_SIZE = 32768;
    public static final int SKIN_128_128_SIZE = 65536;

    private String skinId;
    private String resourcePatch;
    private String geometryData;
    private String animationData;
    private String capeId;
    private String skinColor = "#0";
    private String armSize = "wide";

    private Image skinData;
    private Image capeData;

    private boolean isPremium;
    private boolean isPersona;
    private boolean isCapeOnClassic;
    private boolean isTrusted = false;

    private List<SkinAnimation> skinAnimations = new ArrayList<>();
    private List<PersonaPiece> personaPieces = new ArrayList<>();
    private List<PersonaPieceTint> personaPieceTints = new ArrayList<>();

    public String getSkinId() {
        return this.skinId;
    }

    public void setSkinId( String skinId ) {
        this.skinId = skinId;
    }

    public String getResourcePatch() {
        return this.resourcePatch == null ? "" : this.resourcePatch;
    }

    public void setResourcePatch( String resourcePatch ) {
        this.resourcePatch = resourcePatch;
    }

    public String getGeometryData() {
        return this.geometryData == null ? "" : this.geometryData;
    }

    public void setGeometryData( String geometryData ) {
        this.geometryData = geometryData;
    }

    public String getAnimationData() {
        return this.animationData == null ? "" : this.animationData;
    }

    public void setAnimationData( String animationData ) {
        this.animationData = animationData;
    }

    public String getCapeId() {
        return this.capeId == null ? "" : this.capeId;
    }

    public void setCapeId( String capeId ) {
        this.capeId = capeId;
    }

    public String getSkinColor() {
        return this.skinColor;
    }

    public void setSkinColor( String skinColor ) {
        this.skinColor = skinColor;
    }

    public String getArmSize() {
        return this.armSize;
    }

    public void setArmSize( String armSize ) {
        this.armSize = armSize;
    }

    public Image getSkinData() {
        return this.skinData;
    }

    public void setSkinData( Image skinData ) {
        this.skinData = skinData;
    }

    public Image getCapeData() {
        return this.capeData != null ? this.capeData : new Image( 0, 0, new byte[0] );
    }

    public void setCapeData( Image capeData ) {
        this.capeData = capeData;
    }

    public boolean isPremium() {
        return this.isPremium;
    }

    public void setPremium( boolean premium ) {
        this.isPremium = premium;
    }

    public boolean isPersona() {
        return this.isPersona;
    }

    public void setPersona( boolean persona ) {
        this.isPersona = persona;
    }

    public boolean isCapeOnClassic() {
        return this.isCapeOnClassic;
    }

    public void setCapeOnClassic( boolean capeOnClassic ) {
        this.isCapeOnClassic = capeOnClassic;
    }

    public boolean isTrusted() {
        return this.isTrusted;
    }

    public void setTrusted( boolean trusted ) {
        this.isTrusted = trusted;
    }

    public List<SkinAnimation> getSkinAnimations() {
        return this.skinAnimations;
    }

    public void setSkinAnimations( List<SkinAnimation> skinAnimations ) {
        this.skinAnimations = skinAnimations;
    }

    public List<PersonaPiece> getPersonaPieces() {
        return this.personaPieces;
    }

    public void setPersonaPieces( List<PersonaPiece> personaPieces ) {
        this.personaPieces = personaPieces;
    }

    public List<PersonaPieceTint> getPersonaPieceTints() {
        return this.personaPieceTints;
    }

    public void setPersonaPieceTints( List<PersonaPieceTint> personaPieceTints ) {
        this.personaPieceTints = personaPieceTints;
    }
}
