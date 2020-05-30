package com.xebia.writerpad.util;

import java.util.Collection;
import java.util.Objects;

public final class CommonUtils
{
    public static boolean isEmpty(final Collection collection)
    {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection collection)
    {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(final Object[] arr)
    {
        return Objects.isNull(arr) || arr.length == 0;
    }

    public static boolean isBlank(final String str)
    {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(final String str)
    {
        return !isBlank(str);
    }
}
