/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.servlet;

import javax.servlet.annotation.MultipartConfig;

/**
 * {@link MultipartConfig}アノテーションの値を表現したJavaのクラスです。
 *
 * @since Servlet 3.0
 */
public class MultipartConfigElement {

    private String location;
    private long maxFileSize;
    private long maxRequestSize;
    private int fileSizeThreshold;

    /**
     * ロケーション以外の値をデフォルト値でインスタンスを生成します。
     *
     * @param location 値がnullの場合はデフォルト値として""
     */
    public MultipartConfigElement(String location) {
        if (location == null) {
            this.location = "";
        } else {
            this.location = location;
        }
        this.maxFileSize = -1L;
        this.maxRequestSize = -1L;
        this.fileSizeThreshold = 0;
    }

    /**
     * すべての値を指定してインスタンスを生成します。
     *
     * @param location ファイルが保存されるディレクトリの場所
     * @param maxFileSize アップロードされたファイルが許可される最大のサイズ
     * @param maxRequestSize <tt>multipart/form-data</tt>のリクエストに許可される最大のサイズ
     * @param fileSizeThreshold 超えたときにファイルがディスクに書き込まれるようになるサイズのしきい値
     */
    public MultipartConfigElement(String location, long maxFileSize,
            long maxRequestSize, int fileSizeThreshold) {
        if (location == null) {
            this.location = "";
        } else {
            this.location = location;
        }
        this.maxFileSize = maxFileSize;
        this.maxRequestSize = maxRequestSize;
        this.fileSizeThreshold = fileSizeThreshold;
    }

    /**
     * {@link MultipartConfig}アノテーションの値でインスタンスを生成します。
     *
     * @param annotation アノテーションの値
     */
    public MultipartConfigElement(MultipartConfig annotation) {
        this.location = annotation.location();
        this.fileSizeThreshold = annotation.fileSizeThreshold();
        this.maxFileSize = annotation.maxFileSize();
        this.maxRequestSize = annotation.maxRequestSize();
    }

    /**
     * ファイルが保存されるディレクトリの場所を取得します。
     *
     * @return ファイルが保存されるディレクトリの場所
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * アップロードされたファイルが許可される最大のサイズを取得します。
     *
     * @return アップロードされたファイルが許可される最大のサイズ
     */
    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    /**
     * <tt>multipart/form-data</tt>のリクエストに許可される最大のサイズを取得します。
     *
     * @return <tt>multipart/form-data</tt>のリクエストに許可される最大のサイズ
     */
    public long getMaxRequestSize() {
        return this.maxRequestSize;
    }

    /**
     * 超えたときにファイルがディスクに書き込まれるようになるサイズのしきい値を取得します。
     * 
     * @return 超えたときにファイルがディスクに書き込まれるようになるサイズのしきい値
     */
    public int getFileSizeThreshold() {
        return this.fileSizeThreshold;
    }
}
