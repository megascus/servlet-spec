/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet;


/**
 * サーブレットやフィルターが永続的もしくは一時的に使用できない場合にスローする例外を定義します。
 *
 * <p>サーブレットやフィルターが永続的もしくは一時的に使用できない時、何かが間違っていて、なんらかの手段が講じられるまでそれらはリクエストを扱うことができません。
 * 例えば、サーブレットが正しく設定されていない場合やフィルターが正しくない場合、コンポーネントはエラーと修正のための必要なアクションをログに出力する必要があります。
 *
 * <p>システム全体の問題でリクエストをしばらく処理できない場合に、サーブレットやフィルターが一時的に使用できなくなります。
 * 例えば、3層アーキテクチャでデータ層にアクセスできない場合やリクエストを処理するためのメモリやディスクが不足している場合です。システムアドミニストレーターは修正のためのアクションを取る必要があります。
 * 
 * <p>サーブレットコンテナは両方の使用不能を表す例外を同じ方法で安全に処理することができます。
 * しかしながら、一時的な使用不能をうまく扱うことでサーブレットコンテナはより強固になります。
 * 具体的にはサーブレットコンテナは再起動されるまで、サーブレットやフィルタへのリクエストを拒否するのではなく、例外によって示された期間ブロックします。
 *
 *
 * @author 	Various
 *
 */

public class UnavailableException
extends ServletException {

    private Servlet     servlet;           // what's unavailable
    private boolean     permanent;         // needs admin action?
    private int         seconds;           // unavailability estimate

    /**
     * 
     * @deprecated	Java Servlet API 2.2以降では {@link
     * 			#UnavailableException(String)} を使用してください。
     *
     * @param servlet 	使用できない <code>Servlet</code> のインスタンス
     *
     * @param msg 	説明文
     *
     */
    @Deprecated
    public UnavailableException(Servlet servlet, String msg) {
	super(msg);
	this.servlet = servlet;
	permanent = true;
    }
 
    /**
     * @deprecated	Java Servlet API 2.2以降では {@link
     *			#UnavailableException(String, int)} を使用してください。
     *
     * @param seconds	サーブレットが利用できないと予想される秒数; もしゼロか負の数の場合はサーブレットが永続的に使用できないか使用できない期間を見積もれない場合を表します。
     *
     * @param servlet	使用できない <code>Servlet</code> のインスタンス
     * 
     * @param msg	ログファイルに出力するかユーザーに表示するための説明文
     *
     */
    @Deprecated
    public UnavailableException(int seconds, Servlet servlet, String msg) {
	super(msg);
	this.servlet = servlet;
	if (seconds <= 0)
	    this.seconds = -1;
	else
	    this.seconds = seconds;
	permanent = false;
    }

    /**
     * サーブレットが永続的に使用できないことを説明する文で新しい例外を構築します。
     *
     * @param msg 	説明文
     *
     */

    public UnavailableException(String msg) {
	super(msg);

	permanent = true;
    }

    /**
     * サーブレットが一時的に使用できないことを説明する文と使用できるようになるまでの時間で新しい例外を構築します。
     * 
     * <p>いくつかのケースでサーブレットは使用可能になるまでの時間を見積もれません。
     * サーブレットは実行に必要なサーバーが動作していないことを知ることはできたとしても、動くように修正されるまでの時間を知ることはできません。
     * このような場合は <code>seconds</code> 引数をゼロもしくは負の数で指定します。
     *
     * @param msg	ログファイルに出力するかユーザーに表示するための説明文
     *
     * @param seconds	サーブレットが利用できないと予想される秒数; もしゼロか負の数の場合はサーブレットが永続的に使用できないか使用できない期間を見積もれない場合を表します。
     *
     */
    
    public UnavailableException(String msg, int seconds) {
	super(msg);

	if (seconds <= 0)
	    this.seconds = -1;
	else
	    this.seconds = seconds;

	permanent = false;
    }

    /**
     *
     * サーブレット永続的に使用不能かどうかを <code>boolean</code>で返します。
     * 
     * その場合、サーブレットが何か間違っているのでシステムアドミニストレーターは修正のためのアクションを行わなければいけません。
     *
     * @return		<code>true</code> の場合、サーブレットは永続的に使用できません。
     *          ; <code>false</code>の場合、サーブレットは一時的に使用できません。
     *
     */
     
    public boolean isPermanent() {
	return permanent;
    }
  
    /**
     * @deprecated	Java Servlet API 2.2以降での代替手段はありません。
     *
     * 使用できないことを報告したサーブレットのインスタンスを返します。
     * 
     * @return		<code>UnavailableException</code>をスローした <code>Servlet</code>のインスタンス
     *
     */
    @Deprecated
    public Servlet getServlet() {
	return servlet;
    }

    /**
     * サーブレットが一時的に使用できないと予想される秒数を返します。
     *
     * <p>サーブレットが永続的に使用できない場合やどれくらいの期間使用できないかの予測時間を提供できない場合はこのメソッドは負の数を返します。
     *   最初に例外が報告されてからの経過時間は正確には保たれてはいません。
     *
     * @return		サーブレットが利用できないと予想される秒数、負の数はサーブレットが永続的に使用できないか使用できない期間を見積もれない場合を表します。
     *
     */
     
    public int getUnavailableSeconds() {
	return permanent ? -1 : seconds;
    }
}
