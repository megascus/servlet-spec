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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.servlet.DispatcherType;

/**
 * サーブレットフィルターを宣言するために使用されるアノテーションです。
 *
 * <p>このアノテーションはデプロイ時にコンテナによって処理され、対応するフィルターは指定されたURLパターン、サーブレット、およびディスパッチャーのタイプに適用されます。
 * 
 * @see javax.servlet.Filter
 *
 * @since Servlet 3.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFilter {

    /**
     * フィルターの説明
     * 
     * @return フィルターの説明
     */
    String description() default "";
    
    /**
     * フィルターの表示名
     *
     * @return フィルターの表示名
     */
    String displayName() default "";
    
    /**
     * フィルターの初期化パラメーター
     *
     * @return フィルターの初期化パラメーター
     */
    WebInitParam[] initParams() default {};
    
    /**
     * フィルターの名前
     *
     * @return フィルターの名前
     */
    String filterName() default "";
    
    /**
     * フィルターの小さいアイコン
     *
     * @return フィルターの小さいアイコン
     */
    String smallIcon() default "";

    /**
     * フィルターの大きいアイコン
     *
     * @return フィルターの大きいアイコン
     */
    String largeIcon() default "";

    /**
     * フィルターを適用するサーブレットの名前
     *
     * @return フィルターを適用するサーブレットの名前
     */
    String[] servletNames() default {};
    
    /**
     * フィルターを適用するURLパターン
     * 
     * デフォルトの値は空の配列です。
     *
     * @return フィルターを適用するURLパターン
     */
    String[] value() default {};

    /**
     * フィルターを適用するURLパターン
     *
     * @return フィルターを適用するURLパターン
     */
    String[] urlPatterns() default {};

    /**
     * フィルターを適用するディスパッチャーのタイプ
     *
     * @return フィルターを適用するディスパッチャーのタイプ
     */
    DispatcherType[] dispatcherTypes() default {DispatcherType.REQUEST};
    
    /**
     * フィルターが非同期処理モードをサポートしているかどうかを宣言します。
     *
     * @return {@code true} ならばフィルターが非同期処理モードをサポートしている
     * @see javax.servlet.ServletRequest#startAsync
     * @see javax.servlet.ServletRequest#startAsync(ServletRequest,
     * ServletResponse)
     */
    boolean asyncSupported() default false;

}
