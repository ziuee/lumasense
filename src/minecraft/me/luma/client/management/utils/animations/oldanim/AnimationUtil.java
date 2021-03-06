package me.luma.client.management.utils.animations.oldanim;

import java.util.HashMap;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.utils.animations.oldanim.easings.Back;
import me.luma.client.management.utils.animations.oldanim.easings.Bounce;
import me.luma.client.management.utils.animations.oldanim.easings.Circ;
import me.luma.client.management.utils.animations.oldanim.easings.Cubic;
import me.luma.client.management.utils.animations.oldanim.easings.Quint;
import me.luma.client.management.utils.animations.oldanim.easings.utils.Progression;
import net.minecraft.util.MathHelper;

public class AnimationUtil {

	private Easing easing;
	
	private final HashMap<Integer, Progression> progressions;
	
	public AnimationUtil(Class <? extends Easing> easingClass) {
		try {
			this.easing = easingClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.progressions = new HashMap<Integer, Progression>();
	}
	
	public final double easeIn(Progression progression, double startValue, double endValue, double duration) {
		if (startValue > endValue) {
			final double tempEnd = endValue;
			endValue = startValue;
			startValue = tempEnd;
		}
		if (progression.getValue() < duration) progression.setValue((progression.getValue() + (ClientLoader.loaderInstance.DELTA_UTIL.deltaTime * .001F)));
		if (easing instanceof Quint) {
			return ((Quint) easing).easeIn(progression.getValue(), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final double easeOut(Progression progression, double startValue, double endValue, double duration) {
		if (startValue > endValue) {
			final double tempEnd = endValue;
			endValue = startValue;
			startValue = tempEnd;
		}
		if (progression.getValue() < duration) progression.setValue((progression.getValue() + (ClientLoader.loaderInstance.DELTA_UTIL.deltaTime * .001F)));
		if (easing instanceof Quint) {
			return ((Quint) easing).easeOut(progression.getValue(), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final double easeInOut(Progression progression, double startValue, double endValue, double duration) {
		if (startValue > endValue) {
			final double tempEnd = endValue;
			endValue = startValue;
			startValue = tempEnd;
		}
		if (progression.getValue() < duration) progression.setValue((progression.getValue() + (ClientLoader.loaderInstance.DELTA_UTIL.deltaTime * .001F)));
		if (easing instanceof Quint) {
			return ((Quint) easing).easeInOut(progression.getValue(), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final double easeIn(int animationId, double startValue, double endValue, double duration) {
		if (!this.progressions.containsKey(animationId)) {
			this.progressions.put(animationId, new Progression());
		} else {
			return easeIn(this.progressions.get(animationId), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final double easeOut(int animationId, double startValue, double endValue, double duration) {
		if (!this.progressions.containsKey(animationId)) {
			this.progressions.put(animationId, new Progression());
		} else {
			return easeOut(this.progressions.get(animationId), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final double easeInOut(int animationId, double startValue, double endValue, double duration) {
		if (!this.progressions.containsKey(animationId)) {
			this.progressions.put(animationId, new Progression());
		} else {
			return easeInOut(this.progressions.get(animationId), startValue, endValue, duration);
		}
		return 0;
	}
	
	public final Progression addProgression(int animationId) {
		Progression prog = new Progression();
		if (!this.progressions.containsKey(animationId)) {
			this.progressions.put(animationId, prog);
		} else {
			prog = this.progressions.get(animationId);
		}
		return prog;
	}
	
	public final Progression getProgression(int animationId) {
		Progression prog = null;
		if (!this.progressions.containsKey(animationId)) {
			Progression progNew = new Progression();
			this.progressions.put(animationId, progNew);
			prog = progNew;
		} else {
			prog = this.progressions.get(animationId);
		}
		return prog;
	}
	
	public static final double slide(double current, double min, double max, double speed, boolean sliding) {
		speed *= ClientLoader.loaderInstance.DELTA_UTIL.deltaTime * .2;
		return MathHelper.clamp_double(sliding ? current < max ? current + (max - current) * speed : current : current > min ? current - (current - min) * speed : current, min, max);
	}
	
    public static double animate(double target, double current, double speed) {
        final boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
        }
        else {
            current -= factor;
        }
        return current;
    }
}
