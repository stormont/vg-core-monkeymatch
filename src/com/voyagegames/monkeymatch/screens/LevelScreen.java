package com.voyagegames.monkeymatch.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.DynamicGridImage;
import com.voyagegames.monkeymatch.helpers.GridBox;
import com.voyagegames.monkeymatch.helpers.GridElement;
import com.voyagegames.monkeymatch.helpers.LevelLoader;
import com.voyagegames.monkeymatch.helpers.StaticGridImage;
import com.voyagegames.monkeymatch.helpers.Token;
import com.voyagegames.monkeymatch.helpers.TokenDrag;

public class LevelScreen implements Screen, InputProcessor {
	
	private static final int BONUS_SCORE = 5;
	private static final float BASE_SCALE = 0.6f;
	private static final float POINTS_OFFSET = 1.25f;
	private static final float STANDARD_SCALING = 0.45f;
	private static final float TOKEN_SPACING = 1.5f;
	private static final float ROTATE_ANGLE = 10f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;
	private static final float TIME_2 = 0.5f;
	private static final float TIME_3 = 0.75f;
	private static final float TIME_4 = 1f;
	private static final float TIME_5 = 1.25f;

	private final Random mRandomGenerator = new Random();
	
	private final Stage         mStage;
	private final LevelLoader   mLevel;
	private final LevelCallback mCallback;
	
    private final boolean[] mGridInUse;
    private final boolean[] mGridCollected;
    private final DynamicGridImage[] mGridImages;
    private final Texture[] mGrids;
    private final Texture[] mTokenTextures;
    private final Texture[] mGoldTokens;
    private final Texture[] mDigits;
	private final Actor[] mHighlights;
	private final int mGridElements;
	
	private final List<Token> mTokens = new ArrayList<Token>();
	private final List<Token> mTargets = new ArrayList<Token>();
	private final List<GridBox> mGridBoxes = new ArrayList<GridBox>();
	private final List<Actor> mBonuses = new ArrayList<Actor>();
	private final List<Actor> mScoreDigits = new ArrayList<Actor>();
	
	private boolean mVictory;
	private boolean mVictoryDone;
	private float mScale;
	private float mTargetSpawnTime;
	private float mLastTargetTime;
    private float mElapsedTime;
    private TokenDrag mDrag;
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGridBackground;
    private Texture mHighlight;
    private Texture mBonus;
    private Texture mPoints;
    private Texture mTrophy;
	private Actor mPointsActor;
	private int mPointsScore;
    private StaticGridImage mGridBackgroundImage;

	public LevelScreen(final String levelXML, final int score, final LevelCallback callback) throws Exception {
		mStage = new Stage(0, 0, true);
		mLevel = new LevelLoader(levelXML);
		mPointsScore = score;
		mCallback = callback;
		
		mTargetSpawnTime = mLevel.spawnTime;
		mGridElements = mLevel.numRows * mLevel.numCols;
		mGridInUse = new boolean[mGridElements];
		mGridCollected = new boolean[mGridElements];
		mGridImages = new DynamicGridImage[mGridElements];
		mGrids = new Texture[mGridElements];
		mDigits = new Texture[10];
		
		final int tokenLength = mLevel.tokens.size();
		
		mTokenTextures = new Texture[tokenLength];
		mGoldTokens = new Texture[tokenLength];
		mHighlights = new Actor[tokenLength];
	}

	@Override
	public void show() {
        mElapsedTime = 0f;
        
        mBackground = new Texture("backgrounds/bkgd_tutorial.png");
        mBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBorder = new Texture("backgrounds/bkgd_border.png");
        mBorder.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGridBackground = new Texture(mLevel.background);
        mGridBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mHighlight = new Texture("tokens/highlight.png");
        mHighlight.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBonus = new Texture("tokens/banana.png");
        mBonus.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mPoints = new Texture("tokens/bananas.png");
        mPoints.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mTrophy = new Texture("misc/trophy.png");
        mTrophy.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        for (int i = 0; i < mLevel.grids.size(); ++i) {
        	mGrids[i] = new Texture(mLevel.grids.get(i).asset);
        }
        
        for (final Texture t : mGrids) {
        	t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        
        for (int i = 0; i < mDigits.length; ++i) {
        	mDigits[i] = new Texture("misc/digits" + i + ".png");
        }
        
        for (final Texture t : mDigits) {
        	t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        for (int i = 0; i < mLevel.tokens.size(); ++i) {
        	mTokenTextures[i] = new Texture("tokens/" + mLevel.tokens.get(i));
        	mGoldTokens[i] = new Texture("tokens/gold" + mLevel.tokens.get(i));
        	mTokenTextures[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	mGoldTokens[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
	}

	@Override
	public void render(final float delta) {
		synchronized (mCallback) {
			if (mVictoryDone) {
				return;
			}
		
	        update(delta);
	        
	    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        mStage.act(delta);
	        mStage.draw();
		}
	}

	@Override
	public void resize(final int width, final int height) {
		mStage.setViewport(width, height, true);
		
        mStage.clear();
        mTokens.clear();
        mTargets.clear();
        
        for (int i = 0; i < mGridElements; ++i) {
        	mGridInUse[i] = false;
        	mGridCollected[i] = false;
        }
        
        mGridBackgroundImage = new StaticGridImage(mGridBackground, width, height);
        mScale = width < height ?
        		((float)width) / ((float)mBackground.getWidth()) :
        		((float)height) / ((float)mBackground.getHeight());

        final float gridX = (width - (mGridBackgroundImage.image.getWidth() * mScale)) / 2f;
        final float gridY = (height - (mGridBackgroundImage.image.getHeight() * mScale)) / 2f;
        final StaticGridImage background = new StaticGridImage(mBackground, width, height);
        
        background.image.setPosition(0f, 0f);
        background.image.setWidth(width);
        background.image.setHeight(height);
        setupActor(background.image, TIME_0, TIME_1, 1f);
        
        mGridBackgroundImage.image.setPosition(gridX, gridY);
        setupActor(mGridBackgroundImage.image, TIME_0, TIME_1, mScale);

        for (int i = 0; i < mGridElements; ++i) {
        	mGridImages[i] = new DynamicGridImage(
    				mGrids[i],
    				gridX,
    				gridY,
    				mLevel.grids.get(i).x * mScale,
    				mLevel.grids.get(i).y * mScale);
            setupActor(mGridImages[i].image, TIME_5, TIME_2, mScale);
        }

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        final float borderX = ((float)(mBorder.getWidth() - mGridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mBorder.getHeight() - mGridBackground.getHeight())) / 2f;
        
        gridBorder.image.setPosition(gridX - (borderX * mScale), gridY - (borderY * mScale));
        setupActor(gridBorder.image, TIME_0, TIME_1, mScale);

        final float tokenScale = mLevel.tokenScale * mScale;
        float totalTokenWidth = 0f;
        
        for (final Texture t : mTokenTextures) {
        	final float tokenWidth = t.getWidth();
        	totalTokenWidth += tokenWidth * tokenScale * TOKEN_SPACING;
        }

        float offset = (((float)width) - totalTokenWidth) / 2f;
        
        final TextureRegion highlight = new TextureRegion(mHighlight);
        
        for (int i = 0; i < mTokenTextures.length; ++i) {
        	final Texture t = mTokenTextures[i];
            final Image image = new Image(new TextureRegion(t));
            final float imageWidth = image.getWidth() * tokenScale;
            final Vector2 initialPosition = new Vector2(
            		offset + (imageWidth / 2f * mLevel.tokenScale),
            		(BASE_SCALE - mLevel.tokenScale) * image.getHeight() * tokenScale);
            final Image highlightImage = new Image(highlight);
            
            highlightImage.setPosition(initialPosition.x, initialPosition.y);
            setupActor(highlightImage, TIME_2, TIME_2, tokenScale);
            addHighlightActions(highlightImage);
            
            image.setPosition(initialPosition.x, initialPosition.y);
            setupActor(image, TIME_2, TIME_2, tokenScale);
            image.setTouchable(Touchable.enabled);

            mTokens.add(new Token(image, i, initialPosition, 0f, mLevel.tokenValues.get(i)));
            mHighlights[i] = highlightImage;
            offset += imageWidth * TOKEN_SPACING;
        }
        
        final float standardScale = mScale * STANDARD_SCALING;
        
        mPointsActor = new Image(new TextureRegion(mPoints));
        mPointsActor.setPosition(
        		width - (mPointsActor.getWidth() * standardScale * POINTS_OFFSET),
        		height - (mPointsActor.getHeight() * standardScale * POINTS_OFFSET));
        setupActor(mPointsActor, TIME_2, TIME_2, standardScale);
		
        for (int i = 0; i < mLevel.numBonuses; ++i) {
        	final Actor actor = new Image(new TextureRegion(mBonus));
        	final float halfActorWidth = actor.getWidth() * 0.5f * standardScale;
        	final float halfActorHeight = actor.getHeight() * 0.5f * standardScale;
        	
        	actor.setOrigin(
        			halfActorWidth,
        			halfActorHeight);
        	actor.setPosition(
        			((float)i) * halfActorWidth - (halfActorWidth / 2f),
        			height - (halfActorHeight * 2.75f));
            setupActor(actor, TIME_2, TIME_2, standardScale);
            actor.addAction(Actions.sequence(
            			Actions.delay(TIME_3),
            			Actions.forever(
            					Actions.sequence(
            							Actions.rotateTo(ROTATE_ANGLE, TIME_5),
            							Actions.rotateTo(-ROTATE_ANGLE, TIME_5)
            					)
            			)
            	));
            mBonuses.add(actor);
        }
        
        updateScore();
	}

	@Override
	public void dispose() {
		mStage.dispose();
		mBorder.dispose();
		mBackground.dispose();
		mGridBackground.dispose();
		mHighlight.dispose();
		mBonus.dispose();
		mPoints.dispose();
		mTrophy.dispose();
		
		for (final Texture t : mGrids) {
			t.dispose();
		}
		
		for (final Texture t : mTokenTextures) {
			t.dispose();
		}
		
		for (final Texture t : mGoldTokens) {
			t.dispose();
		}
		
		for (final Texture t : mDigits) {
			t.dispose();
		}
	}

	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		if (mVictory == true) {
			return false;
		}
		
		if (mDrag != null) {
			return false;
		}

		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a == null) {
			return false;
		}
		
		for (int i = 0; i < mTokens.size(); ++i) {
			final Token t = mTokens.get(i);
		
			if (t.actor != a) {
				continue;
			}
			
			mDrag = new TokenDrag(t, x, y);
			
			final Actor highlight = mHighlights[i];
			
			highlight.clearActions();
			highlight.addAction(Actions.fadeOut(TIME_1));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		if (mVictory == true) {
			return false;
		}
		
		if (mDrag == null) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = mDrag.y() - y;
		final Actor a = mDrag.token.actor;

		a.setPosition(a.getX() + deltaX, a.getY() + deltaY);
		mDrag.update(x, y);
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (mVictory == true) {
			return false;
		}
		
		if (mDrag == null) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = y - mDrag.y();
		final Token t = mDrag.token;

		t.actor.setPosition(t.actor.getX() + deltaX, t.actor.getY() + deltaY);
		
		final Actor target = getNearestHit(t);
		
		if (target == null) {
			removeBonus();
		} else {
			collectTarget(target, t);
		}
		
		resetToken(t);
		mDrag = null;
		return true;
	}
	
	private void setupActor(final Actor actor, final float delay, final float fadeIn, final float scale) {
		actor.getColor().a = 0f;
		actor.setTouchable(Touchable.disabled);
		actor.addAction(Actions.sequence(
        		Actions.delay(delay),
        		Actions.fadeIn(fadeIn)
        	));
		actor.setScale(scale);
        mStage.addActor(actor);
	}
	
	private void addHighlightActions(final Actor actor) {
        actor.addAction(Actions.forever(Actions.sequence(
    			Actions.fadeIn(TIME_4),
    			Actions.delay(TIME_4),
    			Actions.fadeOut(TIME_4)
    		)));
	}
	
	private void resetToken(final Token token) {
		token.actor.clearActions();
		token.actor.addAction(Actions.sequence(
				Actions.touchable(Touchable.disabled),
				Actions.fadeOut(TIME_1),
				Actions.moveTo(token.initialPosition.x, token.initialPosition.y),
				Actions.touchable(Touchable.enabled),
				Actions.fadeIn(TIME_1)
			));
		
		for (int i = 0; i < mTokens.size(); ++i) {
			if (mTokens.get(i) != token) {
				continue;
			}
			
			final Actor highlight = mHighlights[i];
			
			highlight.clearActions();
			highlight.addAction(Actions.sequence(
					Actions.delay(TIME_1),
					Actions.fadeIn(TIME_1)
				));
			addHighlightActions(highlight);
			break;
		}
	}
	
	private void updateScore() {
        final String score = String.valueOf(mPointsScore);
        final float scale = STANDARD_SCALING * mScale;
        final int length = score.length();
        
        for (final Actor a : mScoreDigits) {
        	a.clearActions();
        	a.addAction(Actions.removeActor());
        }
        
        final float stageWidth = mStage.getWidth();
        final float pointsWidth = mPointsActor.getWidth() * scale * POINTS_OFFSET;
        final float pointsHeight = mPointsActor.getHeight();
        final float pointsY = mPointsActor.getY();
        
        float width = 0f;
        
        for (int i = length - 1; i >= 0; --i) {
        	final int v = Integer.parseInt(score.substring(i, i + 1));
        	final Actor a = new Image(new TextureRegion(mDigits[v]));
        	
        	a.setScale(scale);
        	width += (a.getWidth() * scale);
        	a.setPosition(
        			stageWidth - pointsWidth - width,
        			pointsY + ((pointsHeight - a.getHeight()) * scale / 2f));
        	mStage.addActor(a);
        	mScoreDigits.add(a);
        }
	}
	
	private void spawnTarget(final int gridIndex, final int tokenIndex) {
		if (
				gridIndex  < 0 || gridIndex  > mGridImages.length ||
				tokenIndex < 0 || tokenIndex > mGoldTokens.length) {
			return;
		}
		
    	final Texture t = mGoldTokens[tokenIndex];
        final float gridX = (mStage.getWidth() - (mGridBackgroundImage.image.getWidth() * mScale)) / 2f;
        final float gridY = (mStage.getHeight() - (mGridBackgroundImage.image.getHeight() * mScale)) / 2f;
        final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
        final GridElement e = mLevel.grids.get(gridIndex);
        
        image.setPosition(((e.x + mLevel.tokenX) * mScale) + gridX, ((e.y + mLevel.tokenY) * mScale) + gridY);
        setupActor(image, 2f, 0.5f, mLevel.tokenScale * mScale);
        mTargets.add(new Token(image, tokenIndex, null, 0f, 0));
        mGridBoxes.add(new GridBox(gridIndex, image, mGridImages[gridIndex].image));
	}
	
	private void update(final float delta) {
        mElapsedTime += delta;
        
		if (mVictory == true) {
			return;
		}
		
		checkForNewSpawn();
		
        int countCollected = 0;
        
        for (int i = 0; i < mGridCollected.length; ++i) {
        	if (mGridCollected[i] == true) {
        		++countCollected;
        	}
        }
        
        if (countCollected == mGridElements) {
        	mVictory = true;
        	performVictory();
        	return;
        }
	}
	
	private void performVictory() {
		for (int i = 0; i < mBonuses.size(); ++i) {
			final Actor bonus = mBonuses.get(i);
			
			bonus.clearActions();
			bonus.addAction(Actions.sequence(
					Actions.delay(TIME_2 * i),
					new Action() {

						@Override
						public boolean act(final float delta) {
							addScore(BONUS_SCORE);
							return true;
						}
						
					},
					Actions.color(Color.GREEN),
						Actions.moveBy(0f, -bonus.getHeight() / 2f, TIME_2),
						Actions.removeActor()
					));

		}

        final Actor actor = new Image(new TextureRegion(mTrophy));
        
        actor.setScale(mScale);
        actor.setPosition(
        		(mStage.getWidth() - (actor.getWidth() * mScale)) / 2f,
        		(mStage.getHeight() - (actor.getHeight() * mScale)) / 2f);
        actor.addAction(Actions.sequence(
        		Actions.delay(TIME_2 * mBonuses.size()),
        		Actions.fadeIn(TIME_2),
        		Actions.delay(TIME_4),
        		new Action() {
        			
					@Override
					public boolean act(float delta) {
						for (final Actor a : mStage.getActors()) {
							if (a == actor) {
								continue;
							}
							
							a.addAction(Actions.sequence(
									Actions.fadeOut(TIME_4),
									Actions.removeActor()
								));
						}
						
						return true;
					}
					
        		},
        		Actions.fadeOut(TIME_4),
        		new Action() {

					@Override
					public boolean act(final float delta) {
						synchronized (mCallback) {
							mVictoryDone = true;
						}
						
						mCallback.levelComplete(mPointsScore);
						return true;
					}
        			
        		},
        		Actions.removeActor()
        	));
		actor.getColor().a = 0f;
		actor.setTouchable(Touchable.disabled);
		mStage.addActor(actor);
	}
	
	private void checkForNewSpawn() {
        if (mLastTargetTime > 0 && (mElapsedTime - mLastTargetTime) < mTargetSpawnTime) {
        	return;
        }
        
        int countInUse = 0;
        
        for (countInUse = 0; countInUse < mGridInUse.length; ++countInUse) {
        	if (mGridInUse[countInUse] == false) {
        		break;
        	}
        }
        
        if (countInUse == mGridElements) {
        	return;
        }
        
        final float weight = mRandomGenerator.nextFloat();
        int tokenIndex = 0;
        
        for (final Float f : mLevel.tokenWeights) {
        	if (weight <= f) {
        		break;
        	}
        	
        	++tokenIndex;
        }
    
    	int gridIndex = 0;
    	
    	do {
    		gridIndex = mRandomGenerator.nextInt(mGridElements);
    	} while (mGridInUse[gridIndex] == true || mGridCollected[gridIndex] == true);
    	
    	spawnTarget(gridIndex, tokenIndex);
    	mGridInUse[gridIndex] = true;
    	mLastTargetTime = mElapsedTime;
	}
	
	private void removeBonus() {
		if (mBonuses.size() == 0) {
			return;
		}
	
		final Actor bonus = mBonuses.remove(mBonuses.size() - 1);
		bonus.clearActions();
		bonus.addAction(Actions.sequence(
					Actions.color(Color.RED),
					Actions.moveBy(0f, -bonus.getHeight() / 2f, TIME_2),
					Actions.removeActor()
				));
	}
	
	private void collectTarget(final Actor target, final Token token) {
		target.remove();
		mTargets.remove(target);
		
		for (final GridBox b : mGridBoxes) {
			if (b.target != target) {
				continue;
			}
		
			b.box.remove();
			mGridBoxes.remove(b);
			mGridCollected[b.offset] = true; 
			break;
		}
		
		addScore(token.value);
	}
	
	private void addScore(final int score) {
		mPointsScore += score;
		mPointsActor.addAction(Actions.sequence(
					Actions.color(Color.GREEN, TIME_1),
					Actions.color(mPointsActor.getColor(), TIME_1)
				));
		updateScore();
	}
	
	private float getDistSquared(final Actor a, final float tokenX, final float tokenY) {
		final float xDist = (a.getX() - tokenX);
		final float yDist = (a.getY() - tokenY);
		return (xDist * xDist) + (yDist * yDist);
	}
	
	private Actor getNearestHit(final Token token) {
		final List<Actor> targets = new ArrayList<Actor>();
		final float tokenWidth = token.actor.getWidth();
		final float tokenHeight = token.actor.getHeight();
		final float radius = tokenHeight < tokenWidth ? tokenHeight / 2f : tokenWidth / 2f;
		final float radiusSq = (radius * radius) * 0.25f;
		final float tokenX = token.actor.getX();
		final float tokenY = token.actor.getY();
		
		for (final Actor a : mStage.getActors()) {
			for (final Token t : mTargets) {
				if (t.actor != a || t.type != token.type) {
					continue;
				}
				
				if (getDistSquared(a, tokenX, tokenY) < radiusSq) {
					targets.add(a);
				}
			}
		}
		
		if (targets.size() == 0) {
			return null;
		}
		
		float closestDist = Float.MAX_VALUE;
		Actor closestTarget = null;
		
		for (final Actor a : targets) {
			final float distSq = getDistSquared(a, tokenX, tokenY);
			
			if (distSq < closestDist) {
				closestDist = distSq;
				closestTarget = a;
			}
		}
		
		return closestTarget;
	}
	
	//
	// Just a bunch of unused required implementation stubs below...
	//

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(final int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(final int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(final int x, final int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
