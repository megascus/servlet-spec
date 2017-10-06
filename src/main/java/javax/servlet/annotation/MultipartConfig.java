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

package javax.servlet.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * {@link javax.servlet.Servlet}クラスに指定できるアノテーションです。
 * <tt>Servlet</tt>のインスタンスが<tt>multipart/form-data</tt> MIMEタイプに準拠するリクエストを期待していることを示します。
 * 
 * <p>MultipartConfigで注釈が付けられたサーブレットは
 * {@link javax.servlet.http.HttpServletRequest#getPart getPart}または{@link javax.servlet.http.HttpServletRequest#getParts getParts}を呼び出すことによって、
 * 特定の<tt>multipart/form-data</tt>リクエスト呼び出しの{@link javax.servlet.http.Part}コンポーネントを取得できます。
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartConfig {

    /**
     * ファイルが保存されるディレクトリの場所です。
     *
     * @return ファイルが保存されるディレクトリの場所
     */
    String location() default "";

    /**
     * アップロードされたファイルが許可される最大のサイズです。
     * 
     * <p>デフォルト値の<tt>-1L</tt>は無制限を表します。
     *
     * @return アップロードされたファイルが許可される最大のサイズ
     */
    long maxFileSize() default -1L;

    /**
     * <tt>multipart/form-data</tt>のリクエストに許可される最大のサイズです。
     * 
     * <p>デフォルト値の<tt>-1L</tt>は無制限を表します。
     *
     * @return <tt>multipart/form-data</tt>のリクエストに許可される最大のサイズ
     */
    long maxRequestSize() default -1L;

    /**
     * 超えたときにファイルがディスクに書き込まれるようになるサイズのしきい値です。
     *
     * @return 超えたときにファイルがディスクに書き込まれるようになるサイズのしきい値
     */
    int fileSizeThreshold() default 0;
}
