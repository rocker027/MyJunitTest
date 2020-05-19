package com.example.myjunittest.ui.spine

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.airbnb.deeplinkdispatch.DeepLink
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.esotericsoftware.spine.*
import com.example.myjunittest.R
import kotlinx.android.synthetic.main.activity_human_oid.*

//@DeepLink({"marksixmarksixmarksixapp://example.com/deepLink/{id}" , "cpcpcpapp://example.com/anotherDeepLink"})
@DeepLink("foo://example.com/deepLink/{id}")
class HumanOidActivity : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_oid)

        val cfg = AndroidApplicationConfiguration()
        cfg.r = 8.also { cfg.a = it }.also { cfg.b = it }.also { cfg.g = it }

        var view : View = initializeForView(HumanOid(), cfg)

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        view.setLayoutParams(layoutParams)
        root.addView(view)

    }
}

class HumanOid : ApplicationAdapter() {
    var camera: OrthographicCamera? = null
    var batch: SpriteBatch? = null
    var renderer: SkeletonRenderer? = null
    var debugRenderer: SkeletonRendererDebug? = null
    var atlas: TextureAtlas? = null
    var skeleton: Skeleton? = null
    var state: AnimationState? = null
    var json: SkeletonJson? = null
    override fun create() {
        camera = OrthographicCamera()
        batch = SpriteBatch()
        renderer = SkeletonRenderer()
        renderer!!.setPremultipliedAlpha(false) // PMA results in correct blending without outlines.
        debugRenderer = SkeletonRendererDebug()
        debugRenderer!!.setBoundingBoxes(false)
        debugRenderer!!.setRegionAttachments(false)
        atlas = TextureAtlas(Gdx.files.internal("humanoid/humanoid.atlas"))
        json = SkeletonJson(atlas) // This loads skeleton JSON data, which is stateless.
        json!!.scale = 0.5f // Load the skeleton at 60% the size it was in Spine.
        val skeletonData =
            json!!.readSkeletonData(Gdx.files.internal("humanoid/humanoid.json"))
        skeleton =
            Skeleton(skeletonData) // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeleton!!.setSkin("elf")
        skeleton!!.setAttachment("back shoes", "humanoid_boots_brown")
        skeleton!!.setAttachment("front shoes", "humanoid_boots_brown")
        skeleton!!.setAttachment("back shoulder", "humanoid_shoulder_brown")
        skeleton!!.setAttachment("front shoulder", "humanoid_shoulder_brown")
        skeleton!!.setAttachment("back weapon", "humanoid_weapon_bow")
        skeleton!!.setAttachment("belt weapon", "humanoid_weapon_knife")
        skeleton!!.setAttachment("body", "humanoid_armor_f_brown")
        skeleton!!.setAttachment("eyebrows", "humanoid_eyebrows_angry")
        skeleton!!.setAttachment("facial hair", "humanoid_beard_3_brown")
        skeleton!!.setAttachment("hair", "humanoid_hair_m_1_brown")
        skeleton!!.setPosition(175f, 50f)
        val stateData =
            AnimationStateData(skeletonData) // Defines mixing (crossfading) between animations.
        stateData.setMix("run", "run", 0.2f)
        state =
            AnimationState(stateData) // Holds the animation state for a skeleton (current animation, time, etc).
        state!!.timeScale = 1.0f // Slow all animations down to 50% speed.

        // Queue animations on track 0.
        state!!.setAnimation(0, "run", true)
        state!!.addAnimation(0, "run", true, 0f) // Run after the jump.
    }

    override fun render() {
        state!!.update(Gdx.graphics.deltaTime) // Update the animation time.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        state!!.apply(skeleton) // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton!!.updateWorldTransform() // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.
        camera!!.update()
        batch!!.projectionMatrix.set(camera!!.combined)
        debugRenderer!!.shapeRenderer.projectionMatrix = camera!!.combined
        batch!!.begin()
        renderer!!.draw(batch, skeleton) // Draw the skeleton images.
        batch!!.end()

//        debugRenderer.draw(skeleton); // Draw debug lines.
    }

    override fun resize(width: Int, height: Int) {
        camera!!.setToOrtho(false) // Update camera with new size.
    }

    override fun dispose() {
        atlas!!.dispose()
    }

    fun animate() {
        state!!.addAnimation(0, "run", true, 0f) // Jump after 2 seconds.
        state!!.addAnimation(0, "draw bow", true, 0f)
        state!!.addAnimation(0, "attack   1", true, 0f)
        state!!.addAnimation(0, "run", true, 0f) // Run after the jump.
    }

    fun setSkin(skin: String?) {
        skeleton!!.setSkin(skin)
    }

    fun setAttachment(slot: String?, attachment: String?) {
        skeleton!!.setAttachment(slot, attachment)
    }

    fun setAnimate(animate: String?) {
        state!!.addAnimation(0, animate, true, 0f)
    }

    fun zoomBig() {
        camera!!.zoom = 0.5f
    }

    fun zoomSmall() {
        camera!!.zoom = 1f
    }
}