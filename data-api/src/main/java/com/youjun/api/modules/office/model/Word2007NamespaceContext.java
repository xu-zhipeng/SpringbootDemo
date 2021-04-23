package com.youjun.api.modules.office.model;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/4/13
 */
public class Word2007NamespaceContext implements NamespaceContext {
    @Override
    public String getNamespaceURI(String prefix) {


        if ("pkg".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/2006/xmlPackage";
        } else if ("wpc".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas";
        } else if ("cx".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2014/chartex";
        } else if ("cx1".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2015/9/8/chartex";
        } else if ("cx2".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2015/10/21/chartex";
        } else if ("cx3".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/9/chartex";
        } else if ("cx4".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/10/chartex";
        } else if ("cx5".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/11/chartex";
        } else if ("cx6".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/12/chartex";
        } else if ("cx7".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/13/chartex";
        } else if ("cx8".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/5/14/chartex";
        } else if ("mc".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/markup-compatibility/2006";
        } else if ("aink".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/ink";
        } else if ("am3d".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2017/model3d";
        } else if ("o".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:office:office";
        } else if ("r".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/officeDocument/2006/relationships";
        } else if ("m".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/officeDocument/2006/math";
        } else if ("v".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:vml";
        } else if ("wp14".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing";
        } else if ("wp".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing";
        } else if ("w10".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:office:word";
        } else if ("w".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/wordprocessingml/2006/main";
        } else if ("w14".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordml";
        } else if ("w15".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2012/wordml";
        } else if ("w16cid".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2016/wordml/cid";
        } else if ("w16se".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2015/wordml/symex";
        } else if ("wpg".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordprocessingGroup";
        } else if ("wpi".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordprocessingInk";
        } else if ("wne".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2006/wordml";
        } else if ("wps".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2010/wordprocessingShape";
        }
        return null;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}
