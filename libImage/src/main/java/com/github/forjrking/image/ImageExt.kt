package com.github.forjrking.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.load.Transformation
import com.github.forjrking.image.core.ImageOptions
import com.github.forjrking.image.core.ImageOptions.DrawableOptions.Companion.DEFAULT
import com.github.forjrking.image.core.OnImageListener
import com.github.forjrking.image.glide.GlideImageLoader
import com.github.forjrking.image.glide.progress.OnProgressListener

/**
 * @description: ImageView扩展实现
 * @author: forjrking
 * @date: 2021/3/30 9:12 AM
 * 加载图片
 */
@JvmOverloads
fun ImageView.loadImage(
    @RawRes @DrawableRes drawableId: Int,
    @RawRes @DrawableRes errorId: Int = drawableId,
) {
    this.loadImage(load = drawableId, placeHolderResId = drawableId, errorResId = errorId)
}

@JvmOverloads
fun ImageView.loadImage(
    url: String?,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
    @RawRes @DrawableRes errorId: Int = placeHolder,
    requestListener: (OnImageListener.() -> Unit)? = null,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = errorId,
        requestListener = requestListener)
}

@JvmOverloads
fun ImageView.loadProgressImage(
    url: String?,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
    progressListener: OnProgressListener? = null,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        onProgressListener = progressListener)
}

@JvmOverloads
fun ImageView.loadResizeImage(
    url: String?,
    width: Int,
    height: Int,
    @RawRes @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        size = ImageOptions.OverrideSize(width, height))
}

@JvmOverloads
fun ImageView.loadGrayImage(
    url: String?,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url,
        placeHolderResId = placeHolder,
        errorResId = placeHolder,
        isGray = true)
}

@JvmOverloads
fun ImageView.loadBlurImage(
    url: String?,
    radius: Int = 25,
    sampling: Int = 4,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isBlur = true, blurRadius = radius, blurSampling = sampling)
}

@JvmOverloads
fun ImageView.loadRoundCornerImage(
    url: String?,
    radius: Int = 0,
    type: ImageOptions.CornerType = ImageOptions.CornerType.ALL,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isRoundedCorners = radius > 0, roundRadius = radius, cornerType = type)
}

@JvmOverloads
fun ImageView.loadCircleImage(
    url: String?,
    borderWidth: Int = 0,
    @ColorInt borderColor: Int = 0,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        isCircle = true, borderWidth = borderWidth, borderColor = borderColor)
}

@JvmOverloads
fun ImageView.loadBorderImage(
    url: String?,
    borderWidth: Int = 0,
    @ColorInt borderColor: Int = 0,
    @DrawableRes placeHolder: Int = DEFAULT.placeHolderResId,
) {
    this.loadImage(load = url, placeHolderResId = placeHolder, errorResId = placeHolder,
        borderWidth = borderWidth, borderColor = borderColor)
}

/**模仿 coil**/
fun ImageView.load(load: Any?, options: (ImageOptions.() -> Unit)? = null) {
    val imageOptions = ImageOptions().also {
        it.res = load
        it.imageView = this
    }
    GlideImageLoader.loadImage(options?.let {
        imageOptions.also(options)
    } ?: imageOptions)
}

@JvmOverloads
fun ImageView.loadImage(
    load: Any?,
    with: Any? = this,
    //占位图 错误图
    @DrawableRes placeHolderResId: Int = DEFAULT.placeHolderResId,
    placeHolderDrawable: Drawable? = DEFAULT.placeHolderDrawable,
    @DrawableRes errorResId: Int = DEFAULT.errorResId,
    errorDrawable: Drawable? = DEFAULT.errorDrawable,
    @DrawableRes fallbackResId: Int = DEFAULT.fallbackResId,
    fallbackDrawable: Drawable? = DEFAULT.fallbackDrawable,
    //缓存策略等
    skipMemoryCache: Boolean = false,
    diskCacheStrategy: ImageOptions.DiskCache = ImageOptions.DiskCache.AUTOMATIC,
    //优先级
    priority: ImageOptions.LoadPriority = ImageOptions.LoadPriority.NORMAL,
    //缩略图
    thumbnail: Float = 0f,
    thumbnailUrl: Any? = null,
    size: ImageOptions.OverrideSize? = null,
    //gif或者动画
    isAnim: Boolean = true,
    isCrossFade: Boolean = false,
    isCircle: Boolean = false,
    isGray: Boolean = false,
    isFitCenter: Boolean = false,
    centerCrop: Boolean = false,
    //输出图像像素格式
    format: Bitmap.Config? = null,
    //边框 一组一起
    borderWidth: Int = 0,
    borderColor: Int = 0,
    //模糊处理 一组一起使用
    isBlur: Boolean = false,
    blurRadius: Int = 25,
    blurSampling: Int = 4,
    //圆角 一组一起使用
    isRoundedCorners: Boolean = false,
    roundRadius: Int = 0,
    cornerType: ImageOptions.CornerType = ImageOptions.CornerType.ALL,
    //自定义转换器
    vararg transformation: Transformation<Bitmap>,
    //进度监听,请求回调监听
    onProgressListener: OnProgressListener = null,
    requestListener: (OnImageListener.() -> Unit)? = null,
    isPlaceHolderUseTransition: Boolean = DEFAULT.isPlaceHolderUseTransition
) {
    val options = ImageOptions().also {
        it.res = load
        it.imageView = this
        it.context = with
        it.placeHolderResId = placeHolderResId
        it.placeHolderDrawable = placeHolderDrawable
        it.errorResId = errorResId
        it.errorDrawable = errorDrawable
        it.fallbackResId = fallbackResId
        it.fallbackDrawable = fallbackDrawable
        it.isCrossFade = isCrossFade
        it.skipMemoryCache = skipMemoryCache
        it.isAnim = isAnim
        it.diskCacheStrategy = diskCacheStrategy
        it.priority = priority
        it.thumbnail = thumbnail
        it.thumbnailUrl = thumbnailUrl
        it.size = size
        it.isCircle = isCircle
        it.isGray = isGray
        it.centerCrop = centerCrop
        it.isFitCenter = isFitCenter
        it.format = format
        it.borderWidth = borderWidth
        it.borderColor = borderColor
        it.isBlur = isBlur
        it.blurRadius = blurRadius
        it.blurSampling = blurSampling
        it.isRoundedCorners = isRoundedCorners
        it.roundRadius = roundRadius
        it.cornerType = cornerType
        it.transformation = transformation
        it.progressListener(onProgressListener)
        requestListener?.let { l -> it.requestListener(l) }
        it.isPlaceHolderUseTransition(isPlaceHolderUseTransition)
    }
    GlideImageLoader.loadImage(options)
}