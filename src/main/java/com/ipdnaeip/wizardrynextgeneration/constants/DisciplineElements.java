package com.ipdnaeip.wizardrynextgeneration.constants;

import electroblob.wizardry.constants.Element;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DisciplineElements {
    public static Set<Element> ALL_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.FIRE, Element.HEALING, Element.ICE, Element.LIGHTNING, Element.MAGIC, Element.NECROMANCY, Element.SORCERY));
    public static Set<Element> DEMON_ELEMENTS = new HashSet<>(Arrays.asList(Element.FIRE, Element.NECROMANCY));
    public static Set<Element> DRACO_ELEMENTS = new HashSet<>(Arrays.asList(Element.FIRE, Element.ICE));
    public static Set<Element> DRUID_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.HEALING));
    public static Set<Element> EARTH_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH));
    public static Set<Element> FIRE_ELEMENTS = new HashSet<>(Arrays.asList(Element.FIRE));
    public static Set<Element> HEALING_ELEMENTS = new HashSet<>(Arrays.asList(Element.HEALING));
    public static Set<Element> HOLY_ELEMENTS = new HashSet<>(Arrays.asList(Element.FIRE, Element.HEALING));
    public static Set<Element> ICE_ELEMENTS = new HashSet<>(Arrays.asList(Element.ICE));
    public static Set<Element> LICH_ELEMENTS = new HashSet<>(Arrays.asList(Element.ICE, Element.NECROMANCY));
    public static Set<Element> LIGHTNING_ELEMENTS = new HashSet<>(Arrays.asList(Element.LIGHTNING));
    public static Set<Element> LUNAR_ELEMENTS = new HashSet<>(Arrays.asList(Element.HEALING, Element.SORCERY));
    public static Set<Element> NATURE_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.LIGHTNING));
    public static Set<Element> NECROMANCY_ELEMENTS = new HashSet<>(Arrays.asList(Element.NECROMANCY));
    public static Set<Element> OCCULT_ELEMENTS = new HashSet<>(Arrays.asList(Element.HEALING, Element.NECROMANCY));
    public static Set<Element> SHAMAN_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.NECROMANCY));
    public static Set<Element> SORCERY_ELEMENTS = new HashSet<>(Arrays.asList(Element.SORCERY));
    public static Set<Element> TERRA_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.FIRE));
    public static Set<Element> VAMPIRE_ELEMENTS = new HashSet<>(Arrays.asList(Element.LIGHTNING, Element.NECROMANCY));
    public static Set<Element> VOID_ELEMENTS = new HashSet<>(Arrays.asList(Element.NECROMANCY, Element.SORCERY));
    public static Set<Element> WITCH_ELEMENTS = new HashSet<>(Arrays.asList(Element.EARTH, Element.SORCERY));

}
