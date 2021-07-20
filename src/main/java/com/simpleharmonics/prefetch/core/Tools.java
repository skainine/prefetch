package com.simpleharmonics.prefetch.core;

public class Tools {

    public static String cleanseRootLink(String rootLink) {
        if (rootLink == null || rootLink.isEmpty()) {
            return null;
        }

        StringBuilder processedLink;
        if (rootLink.startsWith("https://") || rootLink.startsWith("http://")) {
            processedLink = new StringBuilder(rootLink);
        } else {
            return null;
        }

        if (processedLink.length() == 0) {
            return null;
        }

        if (processedLink.lastIndexOf("/") == processedLink.length() - 1) {
            processedLink.deleteCharAt(processedLink.length() - 1);
        }
        return processedLink.toString();
    }

    public static String cleanseRawLink(String rootLink, String rawLink) {
        if (rawLink == null || rawLink.isEmpty()) {
            return null;
        }

        StringBuilder processedLink = new StringBuilder();
        if (rawLink.startsWith(rootLink)) {
            processedLink = new StringBuilder(rawLink);
        } else {
            if (rawLink.startsWith("#")) {
                return null;
            }
            if (rawLink.startsWith("//")) {
                String prefix = rootLink.substring(0, rootLink.indexOf("//"));
                String newString = prefix + rawLink;
                if (newString.startsWith(rootLink)) {
                    processedLink.append(newString);
                } else {
                    return null;
                }
            } else if (rawLink.startsWith("/")) {
                processedLink.append(rootLink);
                processedLink.append(rawLink);
            }
        }

        if (processedLink.length() == 0) {
            return null;
        }

        if (processedLink.lastIndexOf("/") == processedLink.length() - 1) {
            processedLink.deleteCharAt(processedLink.length() - 1);
        }

        return processedLink.toString();
    }
}
