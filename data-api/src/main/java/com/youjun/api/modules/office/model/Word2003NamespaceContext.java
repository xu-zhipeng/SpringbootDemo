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
public class Word2003NamespaceContext implements NamespaceContext {
    @Override
    public String getNamespaceURI(String prefix) {


        if ("aml".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/aml/2001/core";
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
        } else if ("dt".equalsIgnoreCase(prefix.trim())) {
            return "uuid:C2F41010-65B3-11d1-A29F-00AA00C14882";
        } else if ("mc".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.openxmlformats.org/markup-compatibility/2006";
        } else if ("aink".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2016/ink";
        } else if ("am3d".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/drawing/2017/model3d";
        } else if ("o".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:office:office";
        } else if ("v".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:vml";
        } else if ("w10".equalsIgnoreCase(prefix.trim())) {
            return "urn:schemas-microsoft-com:office:word";
        } else if ("w".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2003/wordml";
        } else if ("w16sdtdh".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash";
        } else if ("wx".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2003/auxHint";
        } else if ("wne".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2006/wordml";
        } else if ("wsp".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/office/word/2003/wordml/sp2";
        } else if ("sl".equalsIgnoreCase(prefix.trim())) {
            return "http://schemas.microsoft.com/schemaLibrary/2003/core";
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
